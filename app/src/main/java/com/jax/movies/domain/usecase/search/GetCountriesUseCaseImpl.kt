package com.jax.movies.domain.usecase.search

import com.jax.movies.domain.entity.home.Country
import com.jax.movies.domain.repository.SearchRepository
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountriesUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) : GetCountriesUseCase {
    override suspend fun invoke(query:String): Flow<Resource<Country>> {
        return searchRepository.getCountries(query)
    }
}