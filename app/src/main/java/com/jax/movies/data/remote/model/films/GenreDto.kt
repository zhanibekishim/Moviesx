package com.jax.movies.data.remote.model.films

import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("id") val id: Int,
    @SerializedName("genre") val genre: String,
)
