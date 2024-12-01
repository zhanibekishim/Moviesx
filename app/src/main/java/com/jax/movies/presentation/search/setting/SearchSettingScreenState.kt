package com.jax.movies.presentation.search.setting

data class SearchSettingScreenState(
    val country: String = "",
    val genre: String = "",
    val period: String = "",
    val rating: Double = 0.0,
    val sortingType: SortingType = SortingType.DATE,
    val showType: ShowType = ShowType.DEFAULT,
    val filterType: FilterType = FilterType.YEAR
)
