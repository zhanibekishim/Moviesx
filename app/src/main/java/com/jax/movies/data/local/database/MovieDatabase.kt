package com.jax.movies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jax.movies.data.local.model.FavouriteMovie
import com.jax.movies.data.local.model.SeenMovie

@Database(entities = [FavouriteMovie::class, SeenMovie::class], version = 1)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract val favouriteMoviesDao: FavouriteMoviesDao
    abstract val seenMoviesDao: SeenMoviesDao
}