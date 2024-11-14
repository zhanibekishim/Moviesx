package com.jax.movies.domain.usecase

import com.jax.movies.data.repository.DetailMovieRepositoryImpl
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetGalleriesUseCaseImpl : GetGalleriesUseCase {
    private val repository = DetailMovieRepositoryImpl()
    override suspend operator fun invoke(filmId: Long): Flow<Resource<List<GalleryImage>>> {
        return repository.getGalleries(filmId)
    }
}