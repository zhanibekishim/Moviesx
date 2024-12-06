package com.jax.movies.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.jax.movies.data.local.model.SeenMovie
import com.jax.movies.utils.Constants.SEEN_MOVIES_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface SeenMoviesDao {
    @Upsert
    suspend fun saveSeenMovie(movie: SeenMovie): Long

    @Delete
    suspend fun deleteSeenMovie(movie: SeenMovie)

    @Query("DELETE FROM $SEEN_MOVIES_TABLE")
    suspend fun deleteAllSeenMovies()

    @Query("SELECT EXISTS (SELECT 1 FROM $SEEN_MOVIES_TABLE WHERE id = :movieId)")
    fun exists(movieId: Long): Flow<Boolean>

    @Query("SELECT * FROM $SEEN_MOVIES_TABLE")
    fun getAllSeenMovies(): Flow<List<SeenMovie>>
}