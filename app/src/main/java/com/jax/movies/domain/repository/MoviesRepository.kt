package com.jax.movies.domain.repository

import com.jax.movies.domain.entity.Actor
import com.jax.movies.domain.entity.GalleryImage
import com.jax.movies.utils.Resource
import com.jax.movies.domain.entity.Movie
import com.jax.movies.domain.entity.SimilarMovie
import com.jax.movies.presentation.home.MoviesType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MoviesRepository {
    suspend fun getMovieCollection(type: MoviesType): StateFlow<Resource<List<Movie>>>
    suspend fun getDetailInfo(movieId: Long): Flow<Resource<Movie>>
}