package com.jax.movies.presentation.detail.movies

import com.jax.movies.domain.entity.home.MoviesType

sealed class MoviesScreenAction {
    data class FetchMoviesDetailInfo(val movieType: MoviesType) : MoviesScreenAction()
}