package com.jax.movies.data.local.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jax.movies.data.local.model.FavouriteMovie
import com.jax.movies.utils.Constants.FAVOURITE_MOVIES_TABLE

@Dao
interface FavouriteMoviesDao {
    @Upsert
    suspend fun saveFavouriteMovie(movie: FavouriteMovie)

    @Query("DELETE FROM $FAVOURITE_MOVIES_TABLE")
    suspend fun deleteAllFavouriteMovies()
}