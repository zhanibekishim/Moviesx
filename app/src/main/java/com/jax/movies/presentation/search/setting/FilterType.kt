package com.jax.movies.presentation.search.setting

sealed interface FilterType {
    data class Country(val country: String) : FilterType
    data class Genre(val genre: String) : FilterType
    data class Period(val from: Int, val to: Int) : FilterType
    data class Rating(val rating: Double) : FilterType
}

