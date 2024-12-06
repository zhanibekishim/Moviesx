package com.jax.movies.domain.usecase.profile

import com.jax.movies.domain.repository.ProfileRepository
import javax.inject.Inject

class DeleteAllMoviesCollectionUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : DeleteAllMoviesCollectionUseCase {
    override suspend fun invoke() {
        return repository.deleteMoviesCollection()
    }
}