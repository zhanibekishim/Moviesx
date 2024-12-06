package com.jax.movies.domain.usecase.search

import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.repository.SearchRepository
import com.jax.movies.presentation.search.main.SortBy
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchQueryUseCaseImpl @Inject constructor(
    private val repository: SearchRepository
) : SearchQueryUseCase {
    override suspend fun invoke(query: String,sortBy: SortBy): Flow<Resource<List<Movie>>> {
        return repository.searchQuery(query,sortBy)
    }
}