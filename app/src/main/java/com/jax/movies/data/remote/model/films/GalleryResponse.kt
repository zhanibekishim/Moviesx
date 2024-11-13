package com.jax.movies.data.remote.model.films

import com.google.gson.annotations.SerializedName

data class GalleryResponse(
    @SerializedName("items") val items: List<GalleryImageContainerDto>
)