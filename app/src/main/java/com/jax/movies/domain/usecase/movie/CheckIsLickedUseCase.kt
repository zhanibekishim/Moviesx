package com.jax.movies.domain.usecase.movie

import kotlinx.coroutines.flow.Flow

interface CheckIsLickedUseCase {
     operator fun invoke(movieId: Long): Flow<Boolean>
}