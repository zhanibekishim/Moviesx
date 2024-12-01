package com.jax.movies.domain.usecase.profile

import com.jax.movies.domain.entity.home.Movie
import kotlinx.coroutines.flow.Flow

interface GetFavouriteMoviesUseCase {
    operator fun invoke(): Flow<List<Movie>>
}