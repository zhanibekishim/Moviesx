package com.jax.movies.presentation.detail.actor

import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.home.Movie

sealed class ActorScreenIntent {
    data object Default : ActorScreenIntent()
    data object OnClickBack : ActorScreenIntent()
    data class OnMovieClick(val movie: Movie,val actor: Actor) : ActorScreenIntent()
    data class OnFilmographyClick(val actor: Actor) : ActorScreenIntent()
}

