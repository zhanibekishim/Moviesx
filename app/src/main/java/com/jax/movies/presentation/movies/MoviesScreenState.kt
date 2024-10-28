package com.jax.movies.presentation.movies

import com.jax.movies.domain.entity.Movie

sealed class MoviesScreenState {
    data object Initial : MoviesScreenState()
    data object Loading : MoviesScreenState()
    data class Error(val message: String) : MoviesScreenState()
    data class Success(val movies: List<Movie>) : MoviesScreenState()
}