package com.jax.movies.domain.usecase.search

import com.jax.movies.domain.entity.home.Genre
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetGenresUseCase {
    suspend operator fun invoke(query:String): Flow<Resource<Genre>>
}