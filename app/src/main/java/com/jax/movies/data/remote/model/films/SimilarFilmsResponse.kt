package com.jax.movies.data.remote.model.films

import com.google.gson.annotations.SerializedName

data class SimilarFilmsResponse (
    @SerializedName("films") val films: List<SimilarMovieDto>
)