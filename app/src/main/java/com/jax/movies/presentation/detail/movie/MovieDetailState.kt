package com.jax.movies.presentation.detail.movie

import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie

sealed class MovieDetailState {
    data object Initial : MovieDetailState()
    data object Loading : MovieDetailState()
    data class Error(val message: String) : MovieDetailState()
    data class Success(
        val movie: Movie,
        val gallery: List<GalleryImage>,
        val actors: List<Actor>,
        val filmCrew: List<Actor>,
        val similarMovies: List<Movie>
    ) : MovieDetailState()
}