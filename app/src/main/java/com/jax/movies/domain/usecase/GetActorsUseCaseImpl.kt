package com.jax.movies.domain.usecase

import com.jax.movies.data.repository.DetailMovieRepositoryImpl
import com.jax.movies.domain.entity.Actor
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetActorsUseCaseImpl : GetActorsUseCase {
    private val repository = DetailMovieRepositoryImpl()
    override suspend operator fun invoke(filmId: Long): Flow<Resource<List<Actor>>> {
        return repository.getActors(filmId)
    }
}