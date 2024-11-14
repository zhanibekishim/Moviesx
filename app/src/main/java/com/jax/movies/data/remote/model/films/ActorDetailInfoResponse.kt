package com.jax.movies.data.remote.model.films

import com.google.gson.annotations.SerializedName

data class ActorDetailInfoResponse(
    @SerializedName("personId") val actorId: Long,
    @SerializedName("posterUrl") val posterUrl: String,
    @SerializedName("nameRu") val nameRu: String,
    @SerializedName("nameEn") val nameEn: String,
    @SerializedName("profession") val professions: String,
    @SerializedName("films") val films: List<FilmDto>,
)