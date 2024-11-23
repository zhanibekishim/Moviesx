package com.jax.movies.di

import com.jax.movies.presentation.detail.actor.ActorDetailViewModel
import com.jax.movies.presentation.detail.filmography.FilmographyViewModel
import com.jax.movies.presentation.detail.gallery.GalleryViewModel
import com.jax.movies.presentation.detail.movie.MovieDetailViewModel
import com.jax.movies.presentation.detail.movies.MoviesDetailViewModel
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