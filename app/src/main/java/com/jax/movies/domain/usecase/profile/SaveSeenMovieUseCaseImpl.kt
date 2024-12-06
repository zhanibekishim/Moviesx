package com.jax.movies.domain.usecase.profile

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.repository.DetailMovieRepository
import javax.inject.Inject

class SaveSeenMovieUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
) : SaveSeenMovieUseCase {
    override suspend fun invoke(movie: Movie): Boolean = repository.saveSeenMovie(movie)
}