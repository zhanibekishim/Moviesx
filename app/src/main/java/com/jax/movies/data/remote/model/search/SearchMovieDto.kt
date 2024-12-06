package com.jax.movies.data.remote.model.search

import com.google.gson.annotations.SerializedName
import com.jax.movies.data.remote.model.home.CountryNameContainerDto
import com.jax.movies.data.remote.model.home.GenreNameContainerDto

data class SearchMovieDto(
    @SerializedName("filmId") val id: Long,
    @SerializedName("ratingKinopoisk") val ratingKp: Double,
    @SerializedName("nameRu") val name: String?,
    @SerializedName("year") val year: Int = 0,
    @SerializedName("genres") val genres: List<GenreNameContainerDto>,
    @SerializedName("countries") val countries: List<CountryNameContainerDto>,
    @SerializedName("posterUrl") val posterUrl: String,
)