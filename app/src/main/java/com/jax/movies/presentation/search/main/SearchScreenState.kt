package com.jax.movies.presentation.search.main

import com.jax.movies.domain.entity.home.Movie

sealed interface SearchScreenState {
    data object Initial : SearchScreenState
    data object Loading : SearchScreenState
    data class Error(val message: String) : SearchScreenState
    data class Success(val movies: List<Movie>) : SearchScreenState
}