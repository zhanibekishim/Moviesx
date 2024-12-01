package com.jax.movies.domain.repository

import com.jax.movies.domain.entity.home.Movie
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getFavouriteMovies(): Flow<List<Movie>>
    fun getSeenMovies(): Flow<List<Movie>>
}