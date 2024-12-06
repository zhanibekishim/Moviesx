package com.jax.movies.domain.usecase.search

import com.jax.movies.domain.entity.home.Genre
import com.jax.movies.domain.repository.SearchRepository
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenresUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
): GetGenresUseCase {
    override suspend fun invoke(query:String): Flow<Resource<Genre>> {
        return searchRepository.getGenres(query)
    }
}