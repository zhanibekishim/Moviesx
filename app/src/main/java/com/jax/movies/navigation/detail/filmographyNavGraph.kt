package com.jax.movies.navigation.detail

import android.app.Activity
import android.os.Build
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jax.movies.di.ViewModelModule
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.presentation.detail.filmography.FilmographyScreen
import com.jax.movies.presentation.detail.filmography.FilmographyScreenIntent
import com.jax.movies.presentation.detail.filmography.FilmographyViewModel
import dagger.hilt.android.EntryPointAccessors

fun NavGraphBuilder.filmographyNavGraph(
    navigationState: NavigationState,
) {
    composable(
        route = Details.Filmography.route,
        arguments = listOf(navArgument(Details.FILMOGRAPHY_PARAMETER) { type = Actor.navType })
    ) { navBackStackEntry ->
        val actor = navBackStackEntry.getActor()
        val factory = EntryPointAccessors.fromActivity(
            activity = LocalContext.current as Activity,
            entryPoint = ViewModelModule::class.java
        ).filmographyViewModelFactoryProvider()

        val filmographyViewModel:FilmographyViewModel = viewModel(
            factory = FilmographyViewModel.provideFilmographyViewModel(actor,factory)
        )

        val filmographyIntent =
            filmographyViewModel.filmographyNavigationChannel.collectAsStateWithLifecycle(
                FilmographyScreenIntent.Default
            )
        LaunchedEffect(filmographyIntent.value) {
            when (val currentIntent = filmographyIntent.value) {
                is FilmographyScreenIntent.Default -> {}
                is FilmographyScreenIntent.OnClickBack -> {
                    navigationState.navHostController.popBackStack()
                }

                is FilmographyScreenIntent.OnMovieClick -> {
                    navigationState.navigateToMovieDetailScreen(
                        currentIntent.movie,
                        backRoute = Details.Filmography.route
                    )
                }
            }
        }
        FilmographyScreen(actor = actor, filmographyViewModel = filmographyViewModel)
    }
}

private fun NavBackStackEntry.getActor(): Actor {
    return this.arguments?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            it.getParcelable(Details.FILMOGRAPHY_PARAMETER, Actor::class.java)
        } else {
            @Suppress("DEPRECATION")
            it.getParcelable(Details.FILMOGRAPHY_PARAMETER)
        }
    } ?: throw IllegalStateException("Actor is missing")
}