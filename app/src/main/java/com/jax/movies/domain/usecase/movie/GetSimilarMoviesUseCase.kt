package com.jax.movies.domain.usecase.movie

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetSimilarMoviesUseCase {
    suspend operator fun invoke(filmId: Long): Flow<Resource<List<Movie>>>
}