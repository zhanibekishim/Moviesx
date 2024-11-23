package com.jax.movies.domain.usecase

import com.jax.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class UpdateIsEnteredBeforeUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository
) : UpdateIsEnteredBeforeUseCase {
    override suspend fun updateIsEntered(isEntered: Boolean) {
       /* repository.updateIsEntered(isEntered)*/
        TODO()
    }
}