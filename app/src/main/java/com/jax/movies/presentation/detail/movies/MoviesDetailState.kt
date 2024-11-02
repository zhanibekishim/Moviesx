package com.jax.movies.presentation.detail.movies

import com.jax.movies.domain.entity.Movie
import com.jax.movies.presentation.home.MoviesType

sealed class MoviesDetailState {
    data object Initial : MoviesDetailState()
    data object Loading : MoviesDetailState()
    data class Error(val message: String) : MoviesDetailState()
    data class Success(val movies: List<Movie>,val type: MoviesType) : MoviesDetailState()
}