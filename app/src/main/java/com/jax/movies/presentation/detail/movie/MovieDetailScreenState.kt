package com.jax.movies.presentation.detail.movie

import com.jax.movies.domain.entity.Movie

sealed class MovieDetailScreenState {
    data object Initial : MovieDetailScreenState()
    data object Loading : MovieDetailScreenState()
    data class Error(val message: String) : MovieDetailScreenState()
    data class Success(val movie: Movie) : MovieDetailScreenState()
}