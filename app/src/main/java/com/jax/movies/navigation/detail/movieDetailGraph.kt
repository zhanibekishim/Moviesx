package com.jax.movies.navigation.detail

import android.os.Build
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.presentation.detail.movie.MovieContent
import com.jax.movies.presentation.detail.movie.MovieDetailViewModel
import com.jax.movies.presentation.detail.movie.MovieScreenIntent

fun NavGraphBuilder.movieDetailGraph(
    navigationState: NavigationState
){
    composable(
        route = Details.MovieScreen.route,
        arguments = listOf(navArgument(Details.MOVIE_PARAMETER) { type = Movie.navType })
    ) { backStackEntry ->
        val movie = backStackEntry.getMovie()
        val movieViewModel: MovieDetailViewModel = viewModel()
        val movieDetailIntent =
            movieViewModel.movieNavigationChannel.collectAsStateWithLifecycle(MovieScreenIntent.Default)
        LaunchedEffect(movieDetailIntent.value) {
            when (val currentIntent = movieDetailIntent.value) {
                is MovieScreenIntent.OnActorClick -> {
                    navigationState.navigateToActorScreen(currentIntent.actor)
                }

                is MovieScreenIntent.OnBackClicked -> {
                    navigationState.navHostController.popBackStack()
                }

                is MovieScreenIntent.OnGalleryClick -> {
                    navigationState.navigateToGalleryScreen(currentIntent.movie)
                }

                is MovieScreenIntent.OnMovieClick -> {
                    navigationState.navigateToMovieDetailScreen(
                        movie = currentIntent.toMovie,
                        backRoute = Details.MovieScreen.getRouteWithArgs(currentIntent.fromMovie)
                    )
                }

                is MovieScreenIntent.Default -> {}
                is MovieScreenIntent.OnBlindEyeClick -> {}
                is MovieScreenIntent.OnFavouriteClick -> {}
                is MovieScreenIntent.OnLickClick -> {}
                is MovieScreenIntent.OnMoreClick -> {}
                is MovieScreenIntent.OnShareClick -> {}
            }
        }
        MovieContent(
            movie = movie,
            movieDetailViewModel = movieViewModel
        )
    }
}


private fun NavBackStackEntry.getMovie():Movie{
    return this.arguments?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            it.getParcelable(Details.MOVIE_PARAMETER, Movie::class.java)
        else it.getParcelable(Details.MOVIE_PARAMETER)
    } ?: throw IllegalStateException("Movie is missing")
}