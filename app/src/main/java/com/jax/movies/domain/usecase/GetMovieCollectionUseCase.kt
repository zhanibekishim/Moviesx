package com.jax.movies.domain.usecase

import com.jax.movies.utils.Resource
import com.jax.movies.domain.entity.Movie
import com.jax.movies.presentation.home.MoviesType
import kotlinx.coroutines.flow.StateFlow

interface GetMovieCollectionUseCase{
    suspend operator fun invoke(type: MoviesType): StateFlow<Resource<List<Movie>>>
}