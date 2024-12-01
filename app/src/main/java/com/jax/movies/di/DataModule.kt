package com.jax.movies.di

import android.content.Context
import androidx.room.Room
import com.jax.movies.data.local.database.FavouriteMoviesDao
import com.jax.movies.data.local.database.MovieDatabase
import com.jax.movies.data.local.database.SeenMoviesDao
import com.jax.movies.utils.Constants.MOVIE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return Room.databaseBuilder(
            name = MOVIE_DATABASE,
            context = context,
            klass = MovieDatabase::class.java
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavouriteMoviesDao(movieDatabase: MovieDatabase): FavouriteMoviesDao {
       return movieDatabase.favouriteMoviesDao
    }
    @Singleton
    @Provides
    fun provideSeenMoviesDao(movieDatabase: MovieDatabase): SeenMoviesDao {
        return movieDatabase.seenMoviesDao
    }
}













