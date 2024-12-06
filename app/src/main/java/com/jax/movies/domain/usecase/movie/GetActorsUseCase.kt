package com.jax.movies.domain.usecase.movie

import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetActorsUseCase {
    suspend operator fun invoke(filmId: Long): Flow<Resource<List<Actor>>>
}