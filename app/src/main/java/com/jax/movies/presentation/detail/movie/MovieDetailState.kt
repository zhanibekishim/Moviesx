package com.jax.movies.presentation.detail.movie

import com.jax.movies.domain.entity.Actor
import com.jax.movies.domain.entity.GalleryImage
import com.jax.movies.domain.entity.Movie
import com.jax.movies.domain.entity.SimilarMovie

sealed class MovieDetailState {
    data object Initial : MovieDetailState()
    data object Loading : MovieDetailState()
    data class Error(val message: String) : MovieDetailState()
    data class Success(
        val movie: Movie,
        val gallery: List<GalleryImage>,
        val actors: List<Actor>,
        val similarMovies: List<SimilarMovie>
    ) : MovieDetailState()
}