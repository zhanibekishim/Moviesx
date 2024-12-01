package com.jax.movies.presentation.profile

import com.jax.movies.domain.entity.home.Movie

sealed class ProfileScreenIntent {
    data object Default:ProfileScreenIntent()
    data object OnHomeClick:ProfileScreenIntent()
    data object OnSearchClick : ProfileScreenIntent()
    data class OnMovieClick(val movie: Movie) : ProfileScreenIntent()
}