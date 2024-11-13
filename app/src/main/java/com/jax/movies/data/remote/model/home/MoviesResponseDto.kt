package com.jax.movies.data.remote.model.home

import com.google.gson.annotations.SerializedName

data class MoviesResponseDto(
    @SerializedName("items") val films: List<MovieDto>
)