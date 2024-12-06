package com.jax.movies.utils

import com.jax.movies.R
import com.jax.movies.presentation.profile.CollectionItem
import com.jax.movies.presentation.search.setting.FilterType
import com.jax.movies.presentation.search.setting.ShowType
import com.jax.movies.presentation.search.setting.SortingType

object DefaultLists {
    val yearsList = listOf(
        "1998",
        "1999",
        "2000",
        "2001",
        "2002",
        "2003",
        "2004",
        "2005",
        "2006",
        "2007",
        "2008",
        "2009",
    )

    val defaultSortingType = listOf(
        SortingType.RATING,
        SortingType.YEAR,
        SortingType.NUM_VOTE,
    )
    val defaultShowType = listOf(
        ShowType.ALL,
        ShowType.TV_SERIES,
        ShowType.FILM,
    )
    val defaultFilterTypes = listOf(
        FilterType.Country("Россия"),
        FilterType.Genre("Комедия"),
        FilterType.Period(from = 1998, to = 2008),
        FilterType.Rating(5.0)
    )
    val defaultCollectionTypes = listOf(
        CollectionItem(
            title = "Любимые",
            collectionTypeIcon = R.drawable.icon_liked,
            count = 0
        ),
        CollectionItem(
            title = "Хочу посмотреть",
            collectionTypeIcon = R.drawable.icon_favourite,
            count = 0
        ),
        CollectionItem(
            title = "Хочу посмотреть",
            collectionTypeIcon = R.drawable.icon_favourite,
            count = 0
        ),
    )
}


















