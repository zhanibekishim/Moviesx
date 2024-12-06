package com.jax.movies.domain.usecase.onboarding

interface UpdateIsEnteredBeforeUseCase {
    suspend fun updateIsEntered(isEntered: Boolean)
}