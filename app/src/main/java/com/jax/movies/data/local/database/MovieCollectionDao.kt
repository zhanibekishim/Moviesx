package com.jax.movies.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jax.movies.presentation.components.MovieCollectionItem
import com.jax.movies.utils.Constants.MOVIE_COLLECTION
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieCollectionDao {

    /* @Upsert
     suspend fun insertMovieCollection(movieCollection: MovieCollectionItemDb):Long*/
    /* @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertMovieCollection(movieCollection: MovieCollectionItemDb)

     @Delete
     suspend fun deleteMovieCollection(movieCollection: MovieCollectionItemDb)

     @Query("SELECT * FROM $MOVIE_COLLECTION")
     suspend fun getAllMovieCollections(): Flow<List<MovieCollectionItemDb>>

     @Query("DELETE FROM $MOVIE_COLLECTION")
     suspend fun deleteAllFromMoviesCollection()*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieCollection(movieCollection: MovieCollectionItem)

    @Delete
    suspend fun deleteMovieCollection(movieCollection: MovieCollectionItem)

    @Query("SELECT * FROM $MOVIE_COLLECTION")
    fun getAllMovieCollections(): Flow<List<MovieCollectionItem>>

    @Query("DELETE FROM $MOVIE_COLLECTION")
    suspend fun deleteAllFromMoviesCollection()
    @Query("SELECT * FROM $MOVIE_COLLECTION WHERE name = :name")
    suspend fun getMovieCollectionByName(name: String): MovieCollectionItem?

}