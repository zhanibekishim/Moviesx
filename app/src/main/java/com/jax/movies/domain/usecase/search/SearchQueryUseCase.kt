package com.jax.movies.domain.usecase.search

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SearchQueryUseCase {
    suspend operator fun invoke(query:String): Flow<Resource<List<Movie>>>
}