package com.jax.movies.data.remote.model.films

import com.google.gson.annotations.SerializedName

data class GalleryImageContainerDto(
    @SerializedName("imageUrl") val imageUrl: String?
)