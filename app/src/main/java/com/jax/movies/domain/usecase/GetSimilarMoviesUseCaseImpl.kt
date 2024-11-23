package com.jax.movies.domain.usecase

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.repository.DetailMovieRepository
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSimilarMoviesUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
) : GetSimilarMoviesUseCase {
    override suspend operator fun invoke(filmId: Long): Flow<Resource<List<Movie>>> {
        return repository.getSimilarFilms(filmId)
    }
}