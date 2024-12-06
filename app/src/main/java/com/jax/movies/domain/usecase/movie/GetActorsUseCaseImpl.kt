package com.jax.movies.domain.usecase.movie

import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.repository.DetailMovieRepository
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActorsUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
): GetActorsUseCase {
    override suspend operator fun invoke(filmId: Long): Flow<Resource<List<Actor>>> {
        return repository.getActors(filmId)
    }
}