package com.jax.movies.domain.usecase.movie

import com.jax.movies.domain.repository.DetailMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIsLickedUseCaseImpl @Inject constructor(
    private val movieRepository: DetailMovieRepository
): CheckIsLickedUseCase {
    override fun invoke(movieId: Long): Flow<Boolean> {
        return movieRepository.checkIsLicked(movieId)
    }
}