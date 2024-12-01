package com.jax.movies.domain.usecase.movie

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.repository.DetailMovieRepository
import javax.inject.Inject

class SaveFavouriteMovieUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
) : SaveFavouriteMovieUseCase {
    override suspend operator fun invoke(movie: Movie): Boolean =
        repository.saveFavouriteMovie(movie)
}