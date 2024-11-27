package com.jax.movies.data.repository

import com.jax.movies.data.mapper.MoviesMapper
import com.jax.movies.data.remote.api.MoviesApiService
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.repository.SearchRepository
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: MoviesApiService,
    private val movieMapper: MoviesMapper
) : SearchRepository {
    override suspend fun searchQuery(query: String): Flow<Resource<Movie>> {
        return flow {
            val response = apiService.searchByQuery(query)
            if (response.isSuccessful) {
                response.body()?.films?.map {
                    movieMapper.movieDtoToEntity(it)
                } ?: emit(Resource.Error(Exception("Response body is null")))
            } else {
                emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
            }
        }
    }
}