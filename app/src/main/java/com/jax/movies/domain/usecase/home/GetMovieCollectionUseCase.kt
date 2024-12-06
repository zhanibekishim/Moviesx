package com.jax.movies.domain.usecase.home

import com.jax.movies.utils.Resource
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.entity.home.MoviesType
import kotlinx.coroutines.flow.StateFlow

interface GetMovieCollectionUseCase{
    suspend operator fun invoke(type: MoviesType): StateFlow<Resource<List<Movie>>>
}