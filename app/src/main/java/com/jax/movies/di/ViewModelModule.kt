package com.jax.movies.di

import com.jax.movies.presentation.home.actor.ActorDetailViewModel
import com.jax.movies.presentation.home.filmography.FilmographyViewModel
import com.jax.movies.presentation.home.gallery.GalleryViewModel
import com.jax.movies.presentation.home.movie.MovieDetailViewModel
import com.jax.movies.presentation.home.movies.MoviesDetailViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelModule {
    fun movieDetailViewModelFactoryProvider(): MovieDetailViewModel.MovieDetailFactory
    fun moviesDetailViewModelFactoryProvider(): MoviesDetailViewModel.MoviesDetailFactory
    fun galleryViewModelFactoryProvider(): GalleryViewModel.GalleryViewModelFactory
    fun filmographyViewModelFactoryProvider(): FilmographyViewModel.FilmographyViewModelFactory
    fun actorDetailViewModelFactoryProvider(): ActorDetailViewModel.ActorDetailViewModelFactory
}