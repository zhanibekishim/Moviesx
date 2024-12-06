package com.jax.movies.presentation.search.country

import com.jax.movies.domain.entity.home.Country

sealed interface CountryScreenState {
    data object Initial : CountryScreenState
    data class Loading(val query: String) : CountryScreenState
    data class Error(val error: String, val query: String) : CountryScreenState
    data class Success(
        val countries: Country = defaultCountry,
        val selectedCountry: Country = defaultCountry,
        val query: String = "",
    ) : CountryScreenState
    companion object {
        val defaultCountries = listOf(
            Country(name = "Россия",id = 1),
            Country(name = "Великобритания",id = 2),
            Country(name = "Германия",id = 3),
            Country(name = "Сша",id = 4),
            Country(name = "Франция",id = 5),
        )
        val defaultCountry = Country(name = "Россия",id = 1)
    }
}