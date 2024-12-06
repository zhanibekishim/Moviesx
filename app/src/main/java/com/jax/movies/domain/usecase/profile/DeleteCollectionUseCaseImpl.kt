package com.jax.movies.domain.usecase.profile

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.repository.ProfileRepository
import com.jax.movies.presentation.components.MovieCollectionItem
import javax.inject.Inject

class DeleteCollectionUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : DeleteCollectionUseCase {
    override suspend fun invoke(movieCollectionItem: MovieCollectionItem): Boolean {
        return repository.deleteCollection(movieCollectionItem)
    }
}