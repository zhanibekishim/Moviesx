package com.jax.movies.data.remote.model.films

import com.google.gson.annotations.SerializedName

data class SimilarFilmsResponse (
    @SerializedName("items") val films: List<SimilarMovieDto>
)