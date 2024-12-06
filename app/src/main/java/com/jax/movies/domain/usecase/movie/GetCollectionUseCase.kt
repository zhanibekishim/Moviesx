package com.jax.movies.domain.usecase.movie

import com.jax.movies.presentation.components.MovieCollectionItem
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetCollectionUseCase {
    operator fun invoke(): Flow<List<MovieCollectionItem>>
}