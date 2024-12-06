package com.jax.movies.presentation.search.genre

import com.jax.movies.domain.entity.home.Genre

sealed interface GenreScreenIntent {
    data class OnQueryChange(val query: String) : GenreScreenIntent
    data class OnSearchClick(val query: String) : GenreScreenIntent
    sealed interface Event {
        data object Default:Event
        data object OnClickBack : Event
        data class OnGenreClick(val genre: Genre) : Event
    }
}