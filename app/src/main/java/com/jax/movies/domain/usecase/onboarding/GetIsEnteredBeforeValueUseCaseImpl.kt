package com.jax.movies.domain.usecase.onboarding

import com.jax.movies.domain.repository.MoviesRepository
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIsEnteredBeforeValueUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository
) : GetIsEnteredBeforeValueUseCase {
    override suspend operator fun invoke(): Flow<Resource<Boolean>> {
        /*return repository.getIsEnteredBeforeValue()*/
       TODO()
    }
}