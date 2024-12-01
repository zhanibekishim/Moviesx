package com.jax.movies.presentation.home.movie

import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.home.Movie

sealed class MovieScreenIntent {
    sealed class MovieScreenNavigationIntent : MovieScreenIntent() {
        data class OnGalleryClick(val movie: Movie) : MovieScreenNavigationIntent()
        data class OnBackClicked(val movie: Movie) : MovieScreenNavigationIntent()
        data class OnActorClick(val actor: Actor) : MovieScreenNavigationIntent()
        data class OnMovieClick(val fromMovie: Movie, val toMovie: Movie) : MovieScreenNavigationIntent()
        data object Default : MovieScreenIntent()
    }

    data object OnShareClick : MovieScreenIntent()
    data object OnBlindEyeClick : MovieScreenIntent()
    data object OnMoreClick : MovieScreenIntent()
    data class OnLickClick(val movie: Movie) : MovieScreenIntent()
    data class OnFavouriteClick(val movie: Movie) : MovieScreenIntent()
}

