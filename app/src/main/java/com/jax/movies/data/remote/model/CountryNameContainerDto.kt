package com.jax.movies.data.remote.model

import com.google.gson.annotations.SerializedName

data class CountryNameContainerDto(
    @SerializedName("country") val country: String
)