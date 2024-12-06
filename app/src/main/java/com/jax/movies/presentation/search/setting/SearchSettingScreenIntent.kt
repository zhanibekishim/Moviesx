package com.jax.movies.presentation.search.setting

import com.jax.movies.domain.entity.home.Country
import com.jax.movies.domain.entity.home.Genre

sealed interface SearchSettingScreenIntent {
    data class OnRatingChange(val rating: Double) : SearchSettingScreenIntent
    data class OnPeriodChange(val periodFrom: Int,val periodTo: Int) : SearchSettingScreenIntent
    data class OnCountryChange(val country: Country) : SearchSettingScreenIntent
    data class OnGenreChange(val genre: Genre) : SearchSettingScreenIntent
    data class OnSortingTypeChoose(val sortingType: SortingType) : SearchSettingScreenIntent
    data class OnShowTypeChoose(val showType: ShowType) : SearchSettingScreenIntent
    data class OnFilterTypeChoose(val filterType: FilterType) : SearchSettingScreenIntent
    sealed interface Event {
        data object Default : Event
        data object OnBackClick : Event
        data object OnCountryClick : Event
        data object OnGenreClick : Event
        data object OnPeriodClick : Event
    }
}