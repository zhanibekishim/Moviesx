package com.jax.movies.presentation.detail.films

import com.jax.movies.domain.entity.Movie
import com.jax.movies.presentation.home.MoviesType

sealed class FilmsScreenState {
    data object Initial : FilmsScreenState()
    data object Loading : FilmsScreenState()
    data class Error(val message: String) : FilmsScreenState()
    data class Success(val movies: List<Movie>,val type: MoviesType) : FilmsScreenState()
}