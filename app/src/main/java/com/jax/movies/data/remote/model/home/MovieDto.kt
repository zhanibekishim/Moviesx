package com.jax.movies.data.remote.model.home

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("kinopoiskId") val id: Long,
    @SerializedName("ratingKinopoisk") val ratingKp: Double,
    @SerializedName("nameRu") val name: String?,
    @SerializedName("year") val year: Int = 0,
    @SerializedName("genres") val genres: List<GenreNameContainerDto>,
    @SerializedName("countries") val countries: List<CountryNameContainerDto>,
    @SerializedName("posterUrl") val posterUrl: String,
)