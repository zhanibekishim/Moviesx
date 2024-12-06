package com.jax.movies.domain.usecase.profile

import com.jax.movies.presentation.components.MovieCollectionItem

interface DeleteCollectionUseCase {
    suspend operator fun invoke(movieCollectionItem: MovieCollectionItem): Boolean
}