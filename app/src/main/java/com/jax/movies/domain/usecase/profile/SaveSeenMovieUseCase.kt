package com.jax.movies.domain.usecase.profile

import com.jax.movies.domain.entity.home.Movie

interface SaveSeenMovieUseCase {
    suspend operator fun invoke(movie: Movie):Boolean
}