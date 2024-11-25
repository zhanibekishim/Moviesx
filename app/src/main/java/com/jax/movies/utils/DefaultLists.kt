package com.jax.movies.utils

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

    val defaultGenres = listOf(
        "Комедия",
        "Мелодрама",
        "Боевик",
        "Вестерн",
        "Драма",
    )
    val defaultCountries = listOf(
        "Россия",
        "Великобритания",
        "Германия",
        "Сша",
        "Франция",
    )

    val defaultMovieTypes = listOf(
        "Все",
        "Фильмы",
        "Сериалы",
    )
    val defaultSortTypes = listOf(
        "Дата",
        "Популярность",
        "Рейтинг",
    )
    val defaultFilterTypes = listOf(
        "Страна" to "Россия",
        "Жанр" to "Комедия",
        "Год" to "с 1998 до 2017",
        "Рейтинг" to "любой"
    )

}