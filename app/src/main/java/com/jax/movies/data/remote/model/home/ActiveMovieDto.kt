package com.jax.movies.data.remote.model.home

import com.google.gson.annotations.SerializedName

data class ActiveMovieDto(
    @SerializedName("kinopoiskId") val id: Long,
    @SerializedName("ratingKinopoisk") val ratingKp: Double,
    @SerializedName("nameRu") val name: String,
    @SerializedName("year") val year: Int,
    @SerializedName("posterUrl") val posterUrl: String
)
