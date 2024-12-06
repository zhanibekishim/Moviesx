package com.jax.movies.domain.usecase.movie

import com.jax.movies.presentation.components.MovieCollectionItem

interface AddNewCollectionUseCase {
    suspend operator fun invoke(collectionItem: MovieCollectionItem): Boolean
}