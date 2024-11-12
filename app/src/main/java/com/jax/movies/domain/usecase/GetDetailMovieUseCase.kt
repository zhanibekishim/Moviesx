package com.jax.movies.domain.usecase

import com.jax.movies.utils.Resource
import com.jax.movies.domain.entity.Movie

interface GetDetailMovieUseCase {
    suspend operator fun invoke(id: Long): Resource<Movie>
}