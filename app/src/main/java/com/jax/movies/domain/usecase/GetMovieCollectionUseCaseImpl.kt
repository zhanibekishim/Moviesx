package com.jax.movies.domain.usecase

import com.jax.movies.data.repository.MoviesRepositoryImpl

class GetMovieCollectionUseCaseImpl:GetMovieCollectionUseCase {
    private val repository = MoviesRepositoryImpl()
    override suspend operator fun invoke(type: String) = repository.getMovieCollection(type)
}