package com.jax.movies.domain.usecase

import com.jax.movies.data.repository.MoviesRepositoryImpl
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.repository.MoviesRepository
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailMovieUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository
) : GetDetailMovieUseCase {

    override suspend operator fun invoke(movieId: Long): Flow<Resource<Movie>> {
        return repository.getDetailInfo(movieId)
    }
}