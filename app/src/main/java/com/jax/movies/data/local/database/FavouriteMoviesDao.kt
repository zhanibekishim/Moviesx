package com.jax.movies.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.jax.movies.data.local.model.FavouriteMovie
import com.jax.movies.utils.Constants.FAVOURITE_MOVIES_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteMoviesDao {
    @Upsert
    suspend fun saveFavouriteMovie(movie: FavouriteMovie): Long

    @Delete
    suspend fun deleteFavouriteMovie(movie: FavouriteMovie)

    @Query("DELETE FROM $FAVOURITE_MOVIES_TABLE")
    suspend fun deleteAllFavouriteMovies()

    @Query("SELECT EXISTS(SELECT 1 FROM $FAVOURITE_MOVIES_TABLE WHERE id = :id)")
    fun exists(id: Long): Flow<Boolean>

    @Query("SELECT * FROM $FAVOURITE_MOVIES_TABLE")
    fun getAllFavouriteMovies(): Flow<List<FavouriteMovie>>
}