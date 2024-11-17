package com.jax.movies.domain.repository

import com.jax.movies.utils.Resource
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.entity.home.MoviesType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MoviesRepository {
    suspend fun getMovieCollection(type: MoviesType): StateFlow<Resource<List<Movie>>>
    suspend fun getDetailInfo(movieId: Long): Flow<Resource<Movie>>
}