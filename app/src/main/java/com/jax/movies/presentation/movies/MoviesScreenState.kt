package com.jax.movies.presentation.movies

import com.jax.movies.domain.entity.Movie

data class MoviesListState(
    val moviesList: List<MoviesState>
) {
    sealed interface MoviesState {
        data object Initial : MoviesState
        data object Loading : MoviesState
        data class Error(val message: String) : MoviesState
        data class Success(val movies: List<Movie>, val moviesType: MoviesType) : MoviesState
    }
}



