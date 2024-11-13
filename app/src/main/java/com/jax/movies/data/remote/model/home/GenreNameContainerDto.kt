package com.jax.movies.data.remote.model.home

import com.google.gson.annotations.SerializedName

data class GenreNameContainerDto(
    @SerializedName("genre") val genre:String
)