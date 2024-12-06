package com.jax.movies.domain.repository

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.components.MovieCollectionItem
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getFavouriteMovies(): Flow<List<Movie>>
    fun getSeenMovies(): Flow<List<Movie>>

    suspend fun deleteCollection(movieCollectionItem: MovieCollectionItem): Boolean
    suspend fun deleteSFavouriteMovies()
    suspend fun deleteSeenMovies()
    suspend fun deleteMoviesCollection()
}