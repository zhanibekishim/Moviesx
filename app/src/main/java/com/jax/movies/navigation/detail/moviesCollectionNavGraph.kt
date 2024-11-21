package com.jax.movies.navigation.detail

import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.jax.movies.domain.entity.home.MoviesType
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.presentation.detail.movies.MoviesDetailScreen
import com.jax.movies.presentation.detail.movies.MoviesScreenIntent
import com.jax.movies.presentation.detail.movies.MoviesViewModel

fun NavGraphBuilder.moviesCollectionNavGraph(
   navigationState: NavigationState
){
    composable(Details.MoviesScreen.route) { backStackEntry ->
        val moviesViewModel: MoviesViewModel = viewModel()
        val moviesScreenIntent =
            moviesViewModel.moviesNavigationChannel.collectAsStateWithLifecycle(MoviesScreenIntent.Default)
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
        val movieType = backStackEntry.getMovieType()
        MoviesDetailScreen(
            type = movieType,
            moviesViewModel = moviesViewModel
        )
    }
}

private fun NavBackStackEntry.getMovieType():MoviesType{
    val movieType = this.arguments?.getString(Details.MOVIE_TYPE_PARAMETER)
        ?: throw IllegalStateException("MovieType is missing")
    return Gson().fromJson(movieType, MoviesType::class.java)
}