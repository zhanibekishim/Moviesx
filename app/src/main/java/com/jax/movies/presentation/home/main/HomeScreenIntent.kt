package com.jax.movies.presentation.home.main

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.entity.home.MoviesType

sealed class HomeScreenIntent {
    data object Default : HomeScreenIntent()
    data class OnMovieClick(val movie: Movie) : HomeScreenIntent()
    data class OnMovieTypeClick(val movieType:MoviesType) : HomeScreenIntent()
}
