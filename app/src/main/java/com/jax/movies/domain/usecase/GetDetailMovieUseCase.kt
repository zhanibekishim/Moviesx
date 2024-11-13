package com.jax.movies.domain.usecase

import com.jax.movies.utils.Resource
import com.jax.movies.domain.entity.Movie
import kotlinx.coroutines.flow.Flow

interface GetDetailMovieUseCase {
    suspend operator fun invoke(id: Long): Flow<Resource<Movie>>
}