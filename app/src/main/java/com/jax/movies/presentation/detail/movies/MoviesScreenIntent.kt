package com.jax.movies.presentation.detail.movies

import com.jax.movies.domain.entity.home.Movie

sealed class MoviesScreenIntent {
    data object Default : MoviesScreenIntent()
    data object OnClickBack : MoviesScreenIntent()
    data class OnMovieClick(val movie: Movie) : MoviesScreenIntent()
}
