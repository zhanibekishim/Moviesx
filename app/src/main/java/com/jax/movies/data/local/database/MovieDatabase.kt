package com.jax.movies.data.local.database

import androidx.room.Database
import com.jax.movies.data.local.model.FavouriteMovie
import com.jax.movies.data.local.model.SeenMovie

@Database(entities = [FavouriteMovie::class, SeenMovie::class], version = 1)
abstract class MovieDatabase {
    abstract val favouriteMoviesDao: FavouriteMoviesDao
    abstract val seenMoviesDao: SeenMoviesDao
}