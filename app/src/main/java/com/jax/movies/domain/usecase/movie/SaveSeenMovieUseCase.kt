package com.jax.movies.domain.usecase.movie

import com.jax.movies.domain.entity.home.Movie

interface SaveSeenMovieUseCase {
    suspend operator fun invoke(movie: Movie):Boolean
}