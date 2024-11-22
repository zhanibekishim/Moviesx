package com.jax.movies.domain.usecase

import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetIsEnteredBeforeValueUseCase {
    suspend operator fun invoke(): Flow<Resource<Boolean>>
}