package com.jax.movies.data.remote.model.films

import com.google.gson.annotations.SerializedName

data class FilmDto(
    @SerializedName("filmId") val filmId: Long,
    @SerializedName("nameRu") val nameRu: String,
    @SerializedName("nameEn") val nameEn: String,
    @SerializedName("rating") val rating: Double?,
    @SerializedName("description") val description: String,
    @SerializedName("professionKey") val professionKey: String,
)