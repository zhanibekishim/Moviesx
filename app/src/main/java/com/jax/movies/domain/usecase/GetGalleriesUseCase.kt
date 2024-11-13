package com.jax.movies.domain.usecase

import com.jax.movies.domain.entity.GalleryImage
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetGalleriesUseCase {
    suspend operator fun invoke(filmId: Long): Flow<Resource<List<GalleryImage>>>
}