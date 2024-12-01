package com.jax.movies.domain.usecase.movie

import com.jax.movies.domain.entity.home.Movie

interface SaveFavouriteMovieUseCase {
    suspend operator fun invoke(movie: Movie): Boolean
}