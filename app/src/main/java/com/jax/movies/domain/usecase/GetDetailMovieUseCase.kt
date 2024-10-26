package com.jax.movies.domain.usecase

import com.jax.movies.data.repository.MoviesRepositoryImpl
import com.jax.movies.domain.entity.Movie

class GetDetailMovieUseCase {
    private val repository = MoviesRepositoryImpl()

    suspend operator fun invoke(id:Long): Result<Movie> {
        return repository.getDetailInfo(id)
    }
}