package com.jax.movies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jax.movies.data.local.model.FavouriteMovie
import com.jax.movies.data.local.model.MovieCollectionItemDb
import com.jax.movies.data.local.model.SeenMovie
import com.jax.movies.presentation.components.MovieCollectionItem

@Database(entities = [FavouriteMovie::class, SeenMovie::class, MovieCollectionItem::class], version = 4)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract val favouriteMoviesDao: FavouriteMoviesDao
    abstract val seenMoviesDao: SeenMoviesDao
    abstract val movieCollectionDao: MovieCollectionDao
}