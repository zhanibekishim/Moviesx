package com.jax.movies.domain.repository

import com.jax.movies.domain.entity.Movie
import kotlinx.coroutines.flow.StateFlow

interface MoviesRepository {
    suspend fun getMovieCollection(type:String): StateFlow<Result<List<Movie>>>
    suspend fun getDetailInfo(id: Long): Result<Movie>
}