package com.jax.movies.domain.usecase

import com.jax.movies.data.repository.DetailMovieRepositoryImpl
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

class Test : GetActorDetailInfoUseCase {
    private val repository = DetailMovieRepositoryImpl()
    override suspend operator fun invoke(actorId: Long): Flow<Resource<Actor>> {
        return repository.getActorDetailInfo(actorId)
    }
}