package com.jax.movies.presentation.home.movie

import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.home.Movie

sealed class MovieScreenIntent {
    data object Default : MovieScreenIntent()
    data object OnLickClick : MovieScreenIntent()
    data object OnFavouriteClick : MovieScreenIntent()
    data object OnShareClick : MovieScreenIntent()
    data object OnBlindEyeClick : MovieScreenIntent()
    data object OnMoreClick : MovieScreenIntent()
    data class OnGalleryClick(val movie: Movie) : MovieScreenIntent()
    data class OnBackClicked(val movie: Movie) : MovieScreenIntent()
    data class OnActorClick(val actor: Actor) : MovieScreenIntent()
    data class OnMovieClick(val fromMovie: Movie,val toMovie: Movie) : MovieScreenIntent()
}
