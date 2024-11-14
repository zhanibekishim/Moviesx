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

/*
data class ActorsResponse(
    @SerializedName("personId") val personId: Long,
    @SerializedName("webUrl") val webUrl: String,
    @SerializedName("nameRu") val nameRu: String,
    @SerializedName("nameEn") val nameEn: String,
    @SerializedName("sex") val sex: String,
    @SerializedName("posterUrl") val posterUrl: String,
    @SerializedName("growth") val growth: Long,
    @SerializedName("birthday") val birthday: Any?,
    @SerializedName("death") val death: Any?,
    @SerializedName("age") val age: Long,
    @SerializedName("birthplace") val birthplace: Any?,
    @SerializedName("deathplace") val deathplace: Any?,
    @SerializedName("spouses") val spouses: List<Any?>,
    @SerializedName("hasAwards") val hasAwards: Long,
    @SerializedName("profession") val profession: String,
    @SerializedName("facts") val facts: List<Any?>,
    @SerializedName("films") val films: List<ActorDto>,
)

data class ActorDto(
    @SerializedName("filmId") val filmId: Long,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("rating") val rating: Int?,
    @SerializedName("general") val general: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("professionKey") val professionKey: String,
)
*/
