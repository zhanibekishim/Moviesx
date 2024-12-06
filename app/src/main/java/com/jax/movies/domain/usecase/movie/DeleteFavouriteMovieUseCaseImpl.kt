package com.jax.movies.domain.usecase.movie

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.repository.DetailMovieRepository
import javax.inject.Inject

class DeleteFavouriteMovieUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
): DeleteFavouriteMovieUseCase {
    override suspend fun invoke(movie: Movie) {
        repository.deleteFavouriteMovie(movie)
    }
}