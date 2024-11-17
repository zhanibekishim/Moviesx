package com.jax.movies.presentation.detail.gallery

sealed class GalleryScreenIntent {
    data object Default : GalleryScreenIntent()
    data object OnClickBack : GalleryScreenIntent()
}