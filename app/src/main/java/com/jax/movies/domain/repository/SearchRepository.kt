package com.jax.movies.domain.repository

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchQuery(query:String): Flow<Resource<List<Movie>>>
}