package com.jax.movies.presentation.search.country

import com.jax.movies.domain.entity.home.Country

sealed interface CountryScreenIntent {
    data class OnQueryChange(val query: String) : CountryScreenIntent
    data class OnSearchClick(val query: String) : CountryScreenIntent
    sealed interface Event {
        data object Default:Event
        data object OnClickBack : Event
        data class OnCountryClick(val country: Country) : Event
    }
}
