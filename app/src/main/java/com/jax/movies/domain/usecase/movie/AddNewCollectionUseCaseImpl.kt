package com.jax.movies.domain.usecase.movie

import com.jax.movies.domain.repository.DetailMovieRepository
import com.jax.movies.presentation.components.MovieCollectionItem
import javax.inject.Inject

class AddNewCollectionUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
) : AddNewCollectionUseCase {
    override suspend fun invoke(collectionItem: MovieCollectionItem): Boolean {
        return repository.addNewCollection(collectionItem)
    }
}