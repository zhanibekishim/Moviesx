package com.jax.movies.domain.repository

import com.jax.movies.domain.entity.home.Country
import com.jax.movies.domain.entity.home.Genre
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.search.main.SortBy
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchQuery(query: String, sortBy: SortBy): Flow<Resource<List<Movie>>>
    suspend fun getGenres(query: String): Flow<Resource<Genre>>
    suspend fun getCountries(query: String): Flow<Resource<Country>>
}