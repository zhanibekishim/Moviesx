package com.jax.movies.presentation.detail.film

import com.jax.movies.domain.entity.Movie

sealed class MovieDetailState {
    data object Initial : MovieDetailState()
    data object Loading : MovieDetailState()
    data class Error(val message: String) : MovieDetailState()
    data class Success(val movie: Movie) : MovieDetailState()
}