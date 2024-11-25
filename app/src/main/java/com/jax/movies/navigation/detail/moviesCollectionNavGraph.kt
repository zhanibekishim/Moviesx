package com.jax.movies.navigation.detail

import android.app.Activity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.jax.movies.di.ViewModelModule
import com.jax.movies.domain.entity.home.MoviesType
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.presentation.home.movies.MoviesDetailScreen
import com.jax.movies.presentation.home.movies.MoviesDetailViewModel
import com.jax.movies.presentation.home.movies.MoviesScreenIntent
import dagger.hilt.android.EntryPointAccessors

fun NavGraphBuilder.moviesCollectionNavGraph(
    navigationState: NavigationState
) {
    composable(Details.MoviesScreen.route) { backStackEntry ->
        val movieType = backStackEntry.getMovieType()

        val factory = EntryPointAccessors.fromActivity(
            activity = LocalContext.current as Activity,
            entryPoint = ViewModelModule::class.java
        ).moviesDetailViewModelFactoryProvider()

        val moviesDetailViewModel: MoviesDetailViewModel = viewModel(
            factory = MoviesDetailViewModel.provideMoviesViewModel(movieType, factory)
        )

        val moviesScreenIntent =
            moviesDetailViewModel.moviesNavigationChannel.collectAsStateWithLifecycle(
                MoviesScreenIntent.Default
            )
        LaunchedEffect(moviesScreenIntent.value) {
            when (val currentIntent = moviesScreenIntent.value) {
                is MoviesScreenIntent.Default -> {}
                is MoviesScreenIntent.OnClickBack -> {
                    navigationState.navHostController.popBackStack()
                }

                is MoviesScreenIntent.OnMovieClick -> {
                    navigationState.navigateToMovieDetailScreen(
                        movie = currentIntent.movie,
                        backRoute = Details.MoviesScreen.route
                    )
                }

            }
        }
        MoviesDetailScreen(
            type = movieType,
            moviesDetailViewModel = moviesDetailViewModel
        )
    }
}

private fun NavBackStackEntry.getMovieType(): MoviesType {
    val movieType = this.arguments?.getString(Details.MOVIE_TYPE_PARAMETER)
        ?: throw IllegalStateException("MovieType is missing")
    return Gson().fromJson(movieType, MoviesType::class.java)
}