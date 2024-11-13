package com.jax.movies.domain.usecase

import com.jax.movies.domain.entity.Actor
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetActorsUseCase {
    suspend operator fun invoke(filmId: Long): Flow<Resource<List<Actor>>>
}