package com.jax.movies.domain.usecase

import com.jax.movies.domain.entity.SimilarMovie
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetSimilarMoviesUseCase {
    suspend operator fun invoke(filmId: Long): Flow<Resource<List<SimilarMovie>>>
}