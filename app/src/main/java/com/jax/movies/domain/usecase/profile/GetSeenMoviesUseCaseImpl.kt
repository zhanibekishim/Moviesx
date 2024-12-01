package com.jax.movies.domain.usecase.profile

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSeenMoviesUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : GetSeenMoviesUseCase {
    override fun invoke(): Flow<List<Movie>> = repository.getSeenMovies()
}