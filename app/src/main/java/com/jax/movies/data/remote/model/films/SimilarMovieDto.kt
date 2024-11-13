package com.jax.movies.data.remote.model.films

import com.google.gson.annotations.SerializedName

data class SimilarMovieDto (
    @SerializedName("filmId") val id: Long,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("posterUrl") val posterUrl: String,
)