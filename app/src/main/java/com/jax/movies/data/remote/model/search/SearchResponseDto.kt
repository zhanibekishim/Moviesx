package com.jax.movies.data.remote.model.search

import com.google.gson.annotations.SerializedName
import com.jax.movies.data.remote.model.home.MovieDto

data class SearchResponseDto(
    @SerializedName("films") val films: List<MovieDto>,
)