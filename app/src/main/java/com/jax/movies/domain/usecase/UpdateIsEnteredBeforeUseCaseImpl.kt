package com.jax.movies.domain.usecase

import com.jax.movies.data.repository.MoviesRepositoryImpl
import com.jax.movies.domain.repository.MoviesRepository

class UpdateIsEnteredBeforeUseCaseImpl : UpdateIsEnteredBeforeUseCase {
    private val repository: MoviesRepository = MoviesRepositoryImpl()
    override suspend fun updateIsEntered(isEntered: Boolean) {
        repository.updateIsEntered(isEntered)
    }
}