package com.jax.movies.data.remote.model

import com.google.gson.annotations.SerializedName

data class GenreNameContainerDto(
    @SerializedName("genre") val genre:String
)