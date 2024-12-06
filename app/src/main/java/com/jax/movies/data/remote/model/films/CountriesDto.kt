package com.jax.movies.data.remote.model.films

import com.google.gson.annotations.SerializedName

data class CountriesDto(
    @SerializedName("countries") val countries:List<CountryDto>
)
