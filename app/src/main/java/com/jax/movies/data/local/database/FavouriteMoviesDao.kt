package com.jax.movies.data.local.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jax.movies.data.local.model.FavouriteMovie
import com.jax.movies.utils.Constants.FAVOURITE_MOVIES_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteMoviesDao {
    @Upsert
    suspend fun saveFavouriteMovie(movie: FavouriteMovie): Long

    @Query("DELETE FROM $FAVOURITE_MOVIES_TABLE")
    suspend fun deleteAllFavouriteMovies()

    @Query("SELECT * FROM $FAVOURITE_MOVIES_TABLE")
    fun getAllFavouriteMovies(): Flow<List<FavouriteMovie>>
}