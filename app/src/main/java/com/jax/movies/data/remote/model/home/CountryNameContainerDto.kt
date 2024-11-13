package com.jax.movies.data.remote.model.home

import com.google.gson.annotations.SerializedName

data class CountryNameContainerDto(
    @SerializedName("country") val country: String
)