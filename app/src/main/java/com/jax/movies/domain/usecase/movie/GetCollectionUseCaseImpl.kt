package com.jax.movies.domain.usecase.movie

import com.jax.movies.domain.repository.DetailMovieRepository
import com.jax.movies.presentation.components.MovieCollectionItem
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
) : GetCollectionUseCase {
    override fun invoke(): Flow<List<MovieCollectionItem>> {
        return repository.getCollection()
    }
}