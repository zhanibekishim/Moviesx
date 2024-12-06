package com.jax.movies.presentation.profile

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.components.MovieCollectionItem

sealed class ProfileScreenState {
    data class Success(
        val favouriteMovies: List<Movie>,
        val seenMovies: List<Movie>,
        val collection:List<MovieCollectionItem>
    ) : ProfileScreenState()

    data class Error(val message: String) : ProfileScreenState()
    data object Initial : ProfileScreenState()
}