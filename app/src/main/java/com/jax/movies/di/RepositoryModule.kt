package com.jax.movies.di

import com.jax.movies.data.repository.DetailMovieRepositoryImpl
import com.jax.movies.data.repository.MoviesRepositoryImpl
import com.jax.movies.data.repository.ProfileRepositoryImpl
import com.jax.movies.data.repository.SearchRepositoryImpl
import com.jax.movies.domain.repository.DetailMovieRepository
import com.jax.movies.domain.repository.MoviesRepository
import com.jax.movies.domain.repository.ProfileRepository
import com.jax.movies.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideDetailMovieRepository(detailMovieRepository: DetailMovieRepositoryImpl): DetailMovieRepository

    @Binds
    abstract fun provideMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    abstract fun provideProfileRepository(profileRepository: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun provideSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository
}
