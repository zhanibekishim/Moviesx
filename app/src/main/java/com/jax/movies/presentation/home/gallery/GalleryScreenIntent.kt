package com.jax.movies.presentation.home.gallery

sealed class GalleryScreenIntent {
    data object Default : GalleryScreenIntent()
    data object OnClickBack : GalleryScreenIntent()
}