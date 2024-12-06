package com.jax.movies.domain.usecase.profile

import com.jax.movies.domain.repository.ProfileRepository
import javax.inject.Inject

class DeleteSeenMoviesUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : DeleteSeenMoviesUseCase {
    override suspend fun invoke() {
        repository.deleteSeenMovies()
    }
}