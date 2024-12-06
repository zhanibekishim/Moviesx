package com.jax.movies.domain.usecase.movie

import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.repository.DetailMovieRepository
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActorDetailInfoUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
) : GetActorDetailInfoUseCase {
    override suspend operator fun invoke(actorId: Long): Flow<Resource<Actor>> {
        return repository.getActorDetailInfo(actorId)
    }
}