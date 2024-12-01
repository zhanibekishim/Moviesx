package com.jax.movies.navigation.search

sealed class SearchGraph(val route: String) {
    data object SearchMain:SearchGraph(SEARCH_MAIN)
    data class SearchCountry(val country: String) : SearchGraph(COUNTRY_SCREEN)
    data class SearchGenre(val genre: String) : SearchGraph(GENRE_SCREEN)
    data class SearchPeriod(val from: String, val to: String) : SearchGraph(PERIOD_SCREEN)
    data object SearchSetting : SearchGraph(SETTING_SCREEN)
    companion object {
        const val COUNTRY_SCREEN = "country_screen"
        const val GENRE_SCREEN = "genre_screen"
        const val PERIOD_SCREEN = "period_screen"
        const val SETTING_SCREEN = "setting_screen"
        const val SEARCH_MAIN = "search_screen"

        const val COUNTRY_PARAMETER = "country_parameter"
        const val GENRE_PARAMETER = "genre_parameter"
        const val PERIOD_PARAMETER = "period_parameter"

        const val DEFAULT_COUNTRY = "Russia"
        const val DEFAULT_GENRE = "Comic"
        const val DEFAULT_PERIOD = "1999"
    }
}