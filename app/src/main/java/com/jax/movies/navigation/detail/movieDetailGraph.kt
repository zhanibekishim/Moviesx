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
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.presentation.home.movie.MovieContent
import com.jax.movies.presentation.home.movie.MovieDetailViewModel
import com.jax.movies.presentation.home.movie.MovieScreenIntent
import dagger.hilt.android.EntryPointAccessors

fun NavGraphBuilder.movieDetailGraph(
    navigationState: NavigationState
) {
    composable(
        route = Details.MovieScreen.route,
        arguments = listOf(navArgument(Details.MOVIE_PARAMETER) { type = Movie.navType })
    ) { backStackEntry ->
        val movie = backStackEntry.getMovie()

        val factory = EntryPointAccessors.fromActivity(
            activity = LocalContext.current as Activity,
            entryPoint = ViewModelModule::class.java
        ).movieDetailViewModelFactoryProvider()

        val movieViewModel: MovieDetailViewModel = viewModel(
            factory = MovieDetailViewModel.provideMovieDetailFactory(movie, factory)
        )

        val movieDetailIntent =
            movieViewModel.movieNavigationChannel.collectAsStateWithLifecycle(
                MovieScreenIntent.MovieScreenNavigationIntent.Default
            )
        LaunchedEffect(movieDetailIntent.value) {
            when (val currentIntent = movieDetailIntent.value) {
                is MovieScreenIntent.MovieScreenNavigationIntent.OnActorClick -> {
                    navigationState.navigateToActorScreen(currentIntent.actor)
                }

                is MovieScreenIntent.MovieScreenNavigationIntent.OnBackClicked -> {
                    navigationState.navHostController.popBackStack()
                }

                is MovieScreenIntent.MovieScreenNavigationIntent.OnGalleryClick -> {
                    navigationState.navigateToGalleryScreen(currentIntent.movie)
                }

                is MovieScreenIntent.MovieScreenNavigationIntent.OnMovieClick -> {
                    navigationState.navigateToMovieDetailScreen(
                        movie = currentIntent.toMovie,
                        backRoute = Details.MovieScreen.getRouteWithArgs(currentIntent.fromMovie)
                    )
                }

                is MovieScreenIntent.MovieScreenNavigationIntent.Default -> {}
                is MovieScreenIntent.OnBlindEyeClick -> TODO()
                is MovieScreenIntent.OnFavouriteClick -> TODO()
                is MovieScreenIntent.OnLickClick -> TODO()
                is  MovieScreenIntent.OnMoreClick -> TODO()
                is MovieScreenIntent.OnShareClick -> TODO()
                is MovieScreenIntent.OnCheck -> {}
                is MovieScreenIntent.OnNewCollectionAdd -> {}
            }
        }
        MovieContent(
            movie = movie,
            movieDetailViewModel = movieViewModel
        )
    }
}

private fun NavBackStackEntry.getMovie(): Movie {
    return this.arguments?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            it.getParcelable(Details.MOVIE_PARAMETER, Movie::class.java)
        else it.getParcelable(Details.MOVIE_PARAMETER)
    } ?: throw IllegalStateException("Movie is missing")
}