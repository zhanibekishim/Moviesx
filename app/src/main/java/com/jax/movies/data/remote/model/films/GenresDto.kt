package com.jax.movies.data.remote.model.films

import com.google.gson.annotations.SerializedName

data class GenresDto(
    @SerializedName("genres") val genres:List<GenreDto>
)

