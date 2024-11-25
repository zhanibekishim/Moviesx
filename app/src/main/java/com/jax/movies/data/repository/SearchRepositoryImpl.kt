package com.jax.movies.data.repository

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.repository.SearchRepository
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl : SearchRepository {
    override suspend fun searchQuery(query: String): Flow<Resource<Movie>> {
        TODO("Not yet implemented")
    }
}