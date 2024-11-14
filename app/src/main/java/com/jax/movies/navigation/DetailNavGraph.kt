package com.jax.movies.navigation

import android.net.Uri
import android.os.Build
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.gson.Gson
import com.jax.movies.domain.entity.Movie
import com.jax.movies.presentation.detail.movie.MovieContent
import com.jax.movies.presentation.detail.movies.MoviesDetailScreen
import com.jax.movies.presentation.home.BottomScreenItem
import com.jax.movies.presentation.home.MoviesType

fun NavGraphBuilder.detailsScreen(
    navigationState: NavigationState
) {
    navigation(
        route = GRAPH.DETAILS_GRAPH,
        startDestination = Details.MovieScreen.route
    ) {
        composable(
            route = Details.MovieScreen.route,
            arguments = listOf(navArgument(Details.MOVIE_PARAMETER) { type = Movie.navType })
        ) { backStackEntry ->
            val movie = backStackEntry.arguments?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelable(Details.MOVIE_PARAMETER, Movie::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    it.getParcelable(Details.MOVIE_PARAMETER)
                }
            } ?: throw IllegalStateException("Movie is missing")

            MovieContent(
                movie = movie,
                onBackClicked = {},
                onLikeClicked = {},
                onFavouriteClicked = {},
                onShareClicked = {},
                onBlindEyeClicked = {},
                onMoreClicked = {},
                onMovieClick = {}
            )
        }


        composable(Details.MoviesScreen.route) { backStackEntry ->
            val movieType = backStackEntry.arguments?.getString(Details.MOVIE_TYPE_PARAMETER)
                ?: throw IllegalStateException("Type is missing")
            val type = Gson().fromJson(movieType, MoviesType::class.java)
            MoviesDetailScreen(
                type = type,
                onMovieClick = {
                    navigationState.navigateToMovie(it)
                },
                onClickBack = {
                    navigationState.navigateTo(BottomScreenItem.HomeScreen.route)
                }
            )
        }
    }
}

sealed class Details(val route: String) {
    data object MovieScreen : Details("$BASE_M0VIE_SCREEN/{$MOVIE_PARAMETER}") {
        fun getRouteWithArgs(movie: Movie): String {
            val jsonMovie = Gson().toJson(movie)
            return "$BASE_M0VIE_SCREEN/${jsonMovie.encode()}"
        }
    }

    data object MoviesScreen : Details("$BASE_MOVIES_SCREEN/{$MOVIE_TYPE_PARAMETER}") {
        fun getRouteWithArgs(movieType: MoviesType): String {
            val jsonMovieType = Gson().toJson(movieType)
            return "$BASE_MOVIES_SCREEN/${jsonMovieType.encode()}"
        }
    }

    companion object {
        const val BASE_M0VIE_SCREEN = "MovieScreen"
        const val MOVIE_PARAMETER = "movie"
        const val BASE_MOVIES_SCREEN = "MoviesScreen"
        const val MOVIE_TYPE_PARAMETER = "movieType"
    }
}

private fun String.encode() = Uri.encode(this)
