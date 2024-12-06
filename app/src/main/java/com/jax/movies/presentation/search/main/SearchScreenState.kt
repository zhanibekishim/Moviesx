package com.jax.movies.presentation.search.main

import com.jax.movies.domain.entity.home.Movie

sealed interface SearchScreenState {
    data object Initial : SearchScreenState
    data class Loading(val query: String) : SearchScreenState
    data class Error(val error: String, val query: String) : SearchScreenState
    data class Success(val movies: List<Movie>, val query: String) : SearchScreenState
}