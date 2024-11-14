package com.jax.movies.domain.usecase

import com.jax.movies.data.repository.MoviesRepositoryImpl
import com.jax.movies.utils.Resource
import com.jax.movies.domain.entity.home.Movie
import kotlinx.coroutines.flow.Flow

class GetDetailMovieUseCaseImpl:GetDetailMovieUseCase {
    private val repository = MoviesRepositoryImpl()

    override suspend operator fun invoke(movieId: Long): Flow<Resource<Movie>> {
        return repository.getDetailInfo(movieId)
    }
}