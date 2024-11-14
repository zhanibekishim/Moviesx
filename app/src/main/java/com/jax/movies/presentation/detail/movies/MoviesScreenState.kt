package com.jax.movies.presentation.detail.movies

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.home.MoviesType

sealed class MoviesScreenState {
    data object Initial : MoviesScreenState()
    data object Loading : MoviesScreenState()
    data class Error(val message: String) : MoviesScreenState()
    data class Success(val movies: List<Movie>, val type: MoviesType) : MoviesScreenState()
}