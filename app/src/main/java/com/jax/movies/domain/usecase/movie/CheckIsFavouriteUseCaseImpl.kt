package com.jax.movies.domain.usecase.movie

import com.jax.movies.domain.repository.DetailMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIsFavouriteUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
) : CheckIsFavouriteUseCase {
    override fun invoke(movieId: Long): Flow<Boolean> {
        return repository.checkIsFavourite(movieId)
    }
}