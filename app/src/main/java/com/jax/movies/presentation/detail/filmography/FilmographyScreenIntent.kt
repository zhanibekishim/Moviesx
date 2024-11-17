package com.jax.movies.presentation.detail.filmography

import com.jax.movies.domain.entity.home.Movie

sealed class FilmographyScreenIntent {
    data object Default : FilmographyScreenIntent()
    data object OnClickBack : FilmographyScreenIntent()
    data class OnMovieClick(val movie: Movie) : FilmographyScreenIntent()
}