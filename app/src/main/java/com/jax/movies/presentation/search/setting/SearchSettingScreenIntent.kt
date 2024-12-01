package com.jax.movies.presentation.search.setting

sealed interface SearchSettingScreenIntent {
    data class OnRatingChange(val rating: Double) : SearchSettingScreenIntent
    data class OnCountryChange(val country: String) : SearchSettingScreenIntent
    data class OnGenreChange(val genre: String) : SearchSettingScreenIntent
    data class OnPeriodChange(val period: String) : SearchSettingScreenIntent
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