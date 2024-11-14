package com.jax.movies.domain.usecase

import com.jax.movies.data.repository.MoviesRepositoryImpl
import com.jax.movies.utils.Resource
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.home.MoviesType
import kotlinx.coroutines.flow.StateFlow

class GetMovieCollectionUseCaseImpl:GetMovieCollectionUseCase {
    private val repository = MoviesRepositoryImpl()
    override suspend operator fun invoke(type: MoviesType): StateFlow<Resource<List<Movie>>>{
        return repository.getMovieCollection(type)
    }
}