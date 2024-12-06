package com.jax.movies.domain.usecase.search

import com.jax.movies.domain.entity.home.Country
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetCountriesUseCase {
    suspend operator fun invoke(query: String): Flow<Resource<Country>>
}