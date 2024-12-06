package com.jax.movies.data.remote.model.films

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("id") val id: Int,
    @SerializedName("country") val country: String
)