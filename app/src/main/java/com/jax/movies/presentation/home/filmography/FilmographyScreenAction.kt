package com.jax.movies.presentation.home.filmography

import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.ActorType

sealed class FilmographyScreenAction {
    data class FetchMovies(val actorType: ActorType) : FilmographyScreenAction()
    data class FetchFilmography(val actor: Actor) : FilmographyScreenAction()
}