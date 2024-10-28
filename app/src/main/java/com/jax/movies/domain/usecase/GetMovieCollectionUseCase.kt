package com.jax.movies.domain.usecase

import com.jax.movies.data.repository.MoviesRepositoryImpl

class GetMovieCollectionUseCase {
    private val repository = MoviesRepositoryImpl()
    suspend operator fun invoke(type: String) = repository.getMovieCollection(type)
}