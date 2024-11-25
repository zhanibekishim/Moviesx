package com.jax.movies.presentation.home.filmography

import com.jax.movies.domain.entity.home.Movie

sealed class FilmographyScreenState {
    data object Initial : FilmographyScreenState()
    data object Loading : FilmographyScreenState()
    data class Error(val message: String) : FilmographyScreenState()
    data class Success(val movies: List<Movie>) : FilmographyScreenState()
  /*  data class Success(val movies: Map<ActorType, List<Movie>>) : FilmographyScreenState()*/
}
