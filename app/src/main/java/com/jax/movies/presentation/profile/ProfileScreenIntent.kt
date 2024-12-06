package com.jax.movies.presentation.profile

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.components.MovieCollectionItem

sealed interface ProfileScreenIntent {
    sealed interface Event{
        data object Default : Event
        data object OnHomeClick : Event
        data object OnSearchClick : Event
        data class OnDeleteClick(val name: String) : Event
        data class OnMovieClick(val movie: Movie) : Event
    }
    data class OnAddNewCollection(val name:String):ProfileScreenIntent
    data class OnDeleteCollection(val movieCollectionItem: MovieCollectionItem):ProfileScreenIntent
}