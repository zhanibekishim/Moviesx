package com.jax.movies.domain.usecase

import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.repository.DetailMovieRepository
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGalleriesUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
): GetGalleriesUseCase {
    override suspend operator fun invoke(filmId: Long): Flow<Resource<List<GalleryImage>>> {
        return repository.getGalleries(filmId)
    }
}