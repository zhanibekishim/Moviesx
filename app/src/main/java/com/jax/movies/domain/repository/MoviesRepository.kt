package com.jax.movies.domain.repository

import com.jax.movies.utils.Resource
import com.jax.movies.domain.entity.Movie
import com.jax.movies.presentation.home.MoviesType
import kotlinx.coroutines.flow.StateFlow

interface MoviesRepository {
    suspend fun getMovieCollection(type: MoviesType): StateFlow<Resource<List<Movie>>>
    suspend fun getDetailInfo(id: Long): Resource<Movie>
}