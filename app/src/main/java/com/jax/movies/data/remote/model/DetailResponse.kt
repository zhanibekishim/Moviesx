package com.jax.movies.data.remote.model

import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @SerializedName("kinoposikId") val id:Long,
    @SerializedName("nameRu") val name:String,
    @SerializedName("posterUrl") val posterUrl:String,
    @SerializedName("year") val year:Int,
    @SerializedName("slogan") val slogan:String,
    @SerializedName("shortDescription") val shortDescription: String,
    @SerializedName("description") val description: String,
    @SerializedName("ratingAgeLimits") val ageLimit: String,
    @SerializedName("filmLength") val duration: String,
    @SerializedName("genres") val genres: List<GenreNameContainerDto>,
    @SerializedName("countries") val countries: List<CountryNameContainerDto>
)