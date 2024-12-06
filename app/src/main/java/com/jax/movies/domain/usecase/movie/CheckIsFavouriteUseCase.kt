package com.jax.movies.domain.usecase.movie

import kotlinx.coroutines.flow.Flow

interface CheckIsFavouriteUseCase {
    operator fun invoke(movieId: Long): Flow<Boolean>
}