package com.jax.movies.data.remote.model.search

import com.google.gson.annotations.SerializedName

data class SearchResponseDto(
    @SerializedName("items") val films: List<SearchMovieDto>,
)