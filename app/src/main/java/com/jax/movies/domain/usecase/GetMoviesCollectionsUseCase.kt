package com.jax.movies.domain.usecase

import com.jax.movies.data.repository.MoviesRepositoryImpl

class GetMoviesCollectionsUseCase {
    private val repository = MoviesRepositoryImpl()
    suspend operator fun invoke(types: List<String>) = repository.getMoviesCollections(types)
}