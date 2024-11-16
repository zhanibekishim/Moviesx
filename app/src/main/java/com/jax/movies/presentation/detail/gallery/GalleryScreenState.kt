package com.jax.movies.presentation.detail.gallery

import com.jax.movies.domain.entity.films.GalleryImage

sealed class GalleryScreenState {
    data object Initial : GalleryScreenState()
    data object Loading : GalleryScreenState()
    data class Error(val message: String) : GalleryScreenState()
    data class Success(val galleries: List<GalleryImage>) : GalleryScreenState()
}
