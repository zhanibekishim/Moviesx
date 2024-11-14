package com.jax.movies.domain.usecase

import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetActorDetailInfoUseCase {
    suspend operator fun invoke(actorId: Long): Flow<Resource<Actor>>
}