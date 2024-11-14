package com.jax.movies.domain.usecase

import com.jax.movies.data.repository.DetailMovieRepositoryImpl
import com.jax.movies.domain.entity.Movie
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetSimilarMoviesUseCaseImpl : GetSimilarMoviesUseCase {
    private val repository = DetailMovieRepositoryImpl()
    override suspend operator fun invoke(filmId: Long): Flow<Resource<List<Movie>>> {
        return repository.getSimilarFilms(filmId)
    }
}