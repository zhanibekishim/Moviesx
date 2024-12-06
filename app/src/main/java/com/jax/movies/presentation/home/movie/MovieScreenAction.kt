package com.jax.movies.presentation.home.movie

import com.jax.movies.domain.entity.home.Movie

sealed class MovieScreenAction {
    data object FetchMovieDetailInfo : MovieScreenAction()
}