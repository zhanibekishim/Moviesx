package com.jax.movies.presentation.detail.movies

import com.jax.movies.domain.entity.Movie

sealed class MoviesDetailState {
    data object Initial : MoviesDetailState()
    data object Loading : MoviesDetailState()
    data class Error(val message: String) : MoviesDetailState()
    data class Success(val movies: List<Movie>) : MoviesDetailState()
}