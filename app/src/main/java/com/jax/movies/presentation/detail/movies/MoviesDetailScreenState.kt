package com.jax.movies.presentation.detail.movies

import com.jax.movies.domain.entity.Movie

sealed class MoviesDetailScreenState {
    data object Initial : MoviesDetailScreenState()
    data object Loading : MoviesDetailScreenState()
    data class Error(val message: String) : MoviesDetailScreenState()
    data class Success(val movies: List<Movie>) : MoviesDetailScreenState()
}