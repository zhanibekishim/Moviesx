package com.jax.movies.presentation.detail.actor

import com.jax.movies.domain.entity.films.Actor

sealed class ActorDetailState {
    data object Initial : ActorDetailState()
    data object Loading : ActorDetailState()
    data class Error(val message: String) : ActorDetailState()
    data class Success(val actor: Actor) : ActorDetailState()
}