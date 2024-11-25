package com.jax.movies.presentation.home.actor

import com.jax.movies.domain.entity.films.Actor

sealed class ActorScreenAction {
    data class FetchActorDetailInfo(val actor: Actor) : ActorScreenAction()
}