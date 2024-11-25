package com.jax.movies.presentation.home.gallery

import com.jax.movies.domain.entity.home.Movie

sealed class GalleryScreenAction {
    data class FetchGalleries(val movie: Movie) : GalleryScreenAction()
}