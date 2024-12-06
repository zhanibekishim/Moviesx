package com.jax.movies.domain.usecase.home

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.entity.home.MoviesType
import com.jax.movies.domain.repository.MoviesRepository
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetMovieCollectionUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository
) : GetMovieCollectionUseCase {
    override suspend operator fun invoke(type: MoviesType): StateFlow<Resource<List<Movie>>> {
        return repository.getMovieCollection(type)
    }
}