package com.jax.movies.domain.usecase

interface UpdateIsEnteredBeforeUseCase {
    suspend fun updateIsEntered(isEntered: Boolean)
}