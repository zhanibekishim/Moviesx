package com.jax.movies.presentation.home.movie

import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.components.MovieCollectionItem

sealed interface MovieDetailState {
    data object Initial : MovieDetailState
    data object Loading : MovieDetailState
    data class Error(val message: String) : MovieDetailState
    data class Success(
        val movie: Movie,
        val isFavourite: Boolean,
        val isLicked: Boolean,
        val gallery: List<GalleryImage>,
        val actors: List<Actor>,
        val filmCrew: List<Actor>,
        val similarMovies: List<Movie>,
        val collection: List<MovieCollectionItem>,
        val seenMovieItemCount: Int,
        val favouriteMovieItemCount: Int
    ) : MovieDetailState
}