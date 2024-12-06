package com.jax.movies.di

import android.content.Context
import androidx.room.Room
import com.jax.movies.data.local.database.FavouriteMoviesDao
import com.jax.movies.data.local.database.MovieCollectionDao
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
            context = context,
            klass = MovieDatabase::class.java,
            name = MOVIE_DATABASE
        ).fallbackToDestructiveMigration().build()
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
    @Singleton
    @Provides
    fun provideMovieCollectionDao(movieDatabase: MovieDatabase): MovieCollectionDao {
        return movieDatabase.movieCollectionDao
    }
}













