package com.jax.movies.domain.usecase

import com.jax.movies.data.repository.MoviesRepositoryImpl
import com.jax.movies.domain.entity.Movie
import kotlinx.coroutines.flow.StateFlow

class GetPremiersListUseCase {
    private val repository = MoviesRepositoryImpl()
    suspend operator fun invoke():StateFlow<Result<List<Movie>>>{
       return  repository.getPremiersList()
    }
}
