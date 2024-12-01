package com.jax.movies.presentation.search.setting

enum class FilterType(var double: Double? = null) {
    COUNTRY,
    GENRE,
    YEAR,
    RATING;

    fun updateDouble(value: Double) {
        this.double = value
    }
}

