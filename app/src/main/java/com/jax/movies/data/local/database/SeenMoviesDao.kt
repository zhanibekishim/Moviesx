package com.jax.movies.data.local.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jax.movies.data.local.model.SeenMovie
import com.jax.movies.utils.Constants.SEEN_MOVIES_TABLE

@Dao
interface SeenMoviesDao {
    @Upsert
    fun saveSeenMovie(movie: SeenMovie)

    @Query("DELETE FROM $SEEN_MOVIES_TABLE")
    suspend fun deleteAllSeenMovies()
}