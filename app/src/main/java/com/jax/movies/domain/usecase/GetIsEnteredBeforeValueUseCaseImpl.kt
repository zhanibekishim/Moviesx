package com.jax.movies.domain.usecase

import com.jax.movies.data.repository.MoviesRepositoryImpl
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetIsEnteredBeforeValueUseCaseImpl : GetIsEnteredBeforeValueUseCase {
    private val repository = MoviesRepositoryImpl()
    override suspend operator fun invoke(): Flow<Resource<Boolean>> {
        return repository.getIsEnteredBeforeValue()
    }
}