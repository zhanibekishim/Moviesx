package com.jax.movies.navigation

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.gson.Gson
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.detail.actor.ActorDetailScreen
import com.jax.movies.presentation.detail.filmography.FilmographyScreen
import com.jax.movies.presentation.detail.gallery.GalleryScreen
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
                onBackClicked = {
                    /*navigationState.navigateTo(Details.MoviesScreen.route) */
                    navigationState.navHostController.popBackStack()
                },
                onLikeClicked = {},
                onFavouriteClicked = {},
                onShareClicked = {},
                onBlindEyeClicked = {},
                onMoreClicked = {},
                onMovieClick = {
                    navigationState.navigateToMovie(it)
                },
                onActorClick = {
                    navigationState.navigateTo(Details.ActorsScreen.getRouteWithArgs(it))
                },
                onGalleryClick = {
                    Log.d("sdadasdas", it.id.toString())
                    navigationState.navigateTo(Details.GalleryScreen.getRouteWithArgs(it))
                }
            )
        }

        composable(Details.MoviesScreen.route) { backStackEntry ->
            val movieType = backStackEntry.arguments?.getString(Details.MOVIE_TYPE_PARAMETER)
                ?: throw IllegalStateException("MovieType is missing")
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

        composable(
            route = Details.ActorsScreen.route,
            arguments = listOf(navArgument(Details.ACTOR_PARAMETER) { type = Actor.navType })
        ) { backStackEntry ->
            val actor = backStackEntry.arguments?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelable(Details.ACTOR_PARAMETER, Actor::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    it.getParcelable(Details.ACTOR_PARAMETER)
                }
            } ?: throw IllegalStateException("Actor is missing")
            ActorDetailScreen(
                onMovieClick = {
                    navigationState.navigateToMovie(it)
                },
                onFilmographyClick = {
                    navigationState.navigateTo(Details.Filmography.getRouteWithArgs(it))
                },
                actor = actor
            )
        }
        composable(
            route = Details.GalleryScreen.route,
            arguments = listOf(navArgument(Details.GALLERY_IMAGE_PARAMETER) {
                type = Movie.navType
            })
        ) { navBackStackEntry ->
            val movie = navBackStackEntry.arguments?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelable(Details.GALLERY_IMAGE_PARAMETER, Movie::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    it.getParcelable(Details.GALLERY_IMAGE_PARAMETER)
                }
            } ?: throw IllegalStateException("Movie is missing")
            GalleryScreen(
                movie = movie,
                onClickBack = {
                    navigationState.navHostController.popBackStack()
                }
            )
        }
        composable(
            route = Details.Filmography.route,
            arguments = listOf(navArgument(Details.FILMOGRAPHY_PARAMETER) { type = Actor.navType })
        ) { navBackStackEntry ->
            val actor = navBackStackEntry.arguments?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelable(Details.FILMOGRAPHY_PARAMETER, Actor::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    it.getParcelable(Details.FILMOGRAPHY_PARAMETER)
                }
            } ?: throw IllegalStateException("Actor is missing")
            FilmographyScreen(
                actor = actor,
                onClickBack = {
                    navigationState.navHostController.popBackStack()
                },
                onMovieClick = {
                    navigationState.navigateToMovie(it)
                },
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

    data object ActorsScreen : Details("$BASE_ACTOR_SCREEN/{$ACTOR_PARAMETER}") {
        fun getRouteWithArgs(actor: Actor): String {
            val jsonActor = Gson().toJson(actor)
            return "$BASE_ACTOR_SCREEN/${jsonActor.encode()}"
        }
    }

    data object GalleryScreen : Details("$GALLERY_SCREEN/{$GALLERY_IMAGE_PARAMETER}") {
        fun getRouteWithArgs(movie: Movie): String {
            val jsonMovie = Gson().toJson(movie)
            Log.d("sdadasdas", movie.id.toString())
            return "$GALLERY_SCREEN/${jsonMovie.encode()}"
        }
    }

    data object Filmography : Details("$FILMOGRAPHY_SCREEN/{$FILMOGRAPHY_PARAMETER}") {
        fun getRouteWithArgs(actor: Actor): String {
            val jsonActor = Gson().toJson(actor)
            return "$FILMOGRAPHY_SCREEN/${jsonActor.encode()}"
        }
    }

    companion object {
        const val BASE_M0VIE_SCREEN = "MovieScreen"
        const val MOVIE_PARAMETER = "movieParameter"
        const val BASE_MOVIES_SCREEN = "MoviesScreen"
        const val MOVIE_TYPE_PARAMETER = "movieTypeParameter"
        const val BASE_ACTOR_SCREEN = "ActorScreen"
        const val ACTOR_PARAMETER = "actorParameter"
        const val GALLERY_SCREEN = "GalleryScreen"
        const val GALLERY_IMAGE_PARAMETER = "galleryParameter"
        const val FILMOGRAPHY_SCREEN = "FilmographyScreen"
        const val FILMOGRAPHY_PARAMETER = "filmographyParameter"
    }
}

private fun String.encode() = Uri.encode(this)
