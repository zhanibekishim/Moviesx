package com.jax.movies.domain.usecase

import com.jax.movies.domain.entity.Movie

interface GetDetailMovieUseCase {
    suspend operator fun invoke(id: Long): Result<Movie>
}