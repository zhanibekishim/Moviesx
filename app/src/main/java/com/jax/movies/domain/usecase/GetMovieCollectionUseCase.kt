package com.jax.movies.domain.usecase

import com.jax.movies.domain.entity.Movie
import kotlinx.coroutines.flow.StateFlow

interface GetMovieCollectionUseCase{
    suspend operator fun invoke(type: String): StateFlow<Result<List<Movie>>>
}