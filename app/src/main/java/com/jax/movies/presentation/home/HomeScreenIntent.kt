package com.jax.movies.presentation.home

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.entity.home.MoviesType

sealed class HomeScreenIntent {
    data object Default : HomeScreenIntent()
    data object OnSearchScreenClick : HomeScreenIntent()
    data object OnProfileScreenClick : HomeScreenIntent()
    data class OnMovieClick(val movie: Movie) : HomeScreenIntent()
    data class OnMovieTypeClick(val movieType:MoviesType) : HomeScreenIntent()
}
