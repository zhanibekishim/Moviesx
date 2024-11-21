package com.jax.movies.navigation.detail

import android.net.Uri
import android.util.Log
import com.google.gson.Gson
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.entity.home.MoviesType

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