package com.jax.movies.presentation.search.main

import com.jax.movies.domain.entity.home.Movie

sealed interface SearchScreenIntent {
    data class OnValueChange(val query: String, val sortBy: SortBy) : SearchScreenIntent
    sealed interface Event {
        data object Default : Event
        data object OnFilterClick : Event
        data object OnHomeClick : Event
        data object OnProfileClick : Event
        data class OnMovieClick(val movie: Movie) : Event
    }
}