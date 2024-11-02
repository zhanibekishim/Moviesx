package com.jax.movies.domain.usecase

import com.jax.movies.data.repository.MoviesRepositoryImpl
import com.jax.movies.presentation.home.MoviesType

class GetMovieCollectionUseCaseImpl:GetMovieCollectionUseCase {
    private val repository = MoviesRepositoryImpl()
    override suspend operator fun invoke(type: MoviesType) = repository.getMovieCollection(type)
}