package com.jax.movies.domain.usecase

import com.jax.movies.domain.entity.Movie
import com.jax.movies.presentation.movies.MoviesType
import kotlinx.coroutines.flow.StateFlow

interface GetMovieCollectionUseCase{
    suspend operator fun invoke(type: MoviesType): StateFlow<Result<List<Movie>>>
}