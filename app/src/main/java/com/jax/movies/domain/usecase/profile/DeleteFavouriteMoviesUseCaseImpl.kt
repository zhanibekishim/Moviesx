package com.jax.movies.domain.usecase.profile

import android.provider.ContactsContract.Profile
import com.jax.movies.domain.repository.DetailMovieRepository
import com.jax.movies.domain.repository.ProfileRepository
import javax.inject.Inject

class DeleteFavouriteMoviesUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
): DeleteFavouriteMoviesUseCase {
    override suspend fun invoke() {
        repository.deleteSFavouriteMovies()
    }
}