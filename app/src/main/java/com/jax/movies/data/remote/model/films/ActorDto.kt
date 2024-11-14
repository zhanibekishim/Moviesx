package com.jax.movies.data.remote.model.films

import com.google.gson.annotations.SerializedName

data class ActorDto(
    @SerializedName("staffId") val actorId: Long,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("professionText") val professionText: String,
    @SerializedName("professionKey") val professionKey: String,
    @SerializedName("posterUrl") val posterUrl: String
)
