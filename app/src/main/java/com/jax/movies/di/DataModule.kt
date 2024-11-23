package com.jax.movies.di

import com.jax.movies.data.repository.DetailMovieRepositoryImpl
import com.jax.movies.data.repository.MoviesRepositoryImpl
import com.jax.movies.domain.repository.DetailMovieRepository
import com.jax.movies.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun provideMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    abstract fun provideDetailMovieRepository(detailMovieRepository: DetailMovieRepositoryImpl): DetailMovieRepository
}
