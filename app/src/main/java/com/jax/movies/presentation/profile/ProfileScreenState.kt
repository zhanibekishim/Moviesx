package com.jax.movies.presentation.profile

import com.jax.movies.domain.entity.home.Movie

sealed class ProfileScreenState {
    data class Success(
        val favouriteMovies: List<Movie>,
        val seenMovies: List<Movie>
    ) : ProfileScreenState()

    data class Error(val message: String) : ProfileScreenState()
    data object Initial : ProfileScreenState()
}