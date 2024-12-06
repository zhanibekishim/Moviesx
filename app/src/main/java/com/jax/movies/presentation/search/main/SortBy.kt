package com.jax.movies.presentation.search.main

import com.jax.movies.domain.entity.home.Country
import com.jax.movies.domain.entity.home.Genre
import com.jax.movies.presentation.search.setting.ShowType
import com.jax.movies.presentation.search.setting.SortingType

data class SortBy(
    val genre: Genre= Genre(id = 1, name = "Триллер"),
    val period: Pair<Int, Int> = Pair(1990, 2023),
    val country: Country = Country(id = 1, name = "Россия"),
    val rating: Double = 8.0,
    val showType: ShowType= ShowType.ALL,
    val sort: SortingType = SortingType.RATING
)
