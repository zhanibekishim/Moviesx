package com.jax.movies.presentation.search.setting

data class SearchSettingScreenState(
    val country: String = "",
    val genre: String = "",
    val periodFrom: Int = 1999,
    val periodTo: Int = 2005,
    val rating: Double = 0.0,
    val sortingType: SortingType = SortingType.YEAR,
    val showType: ShowType = ShowType.ALL,
    val filterType: FilterType = FilterType.Country("Россия")
)
