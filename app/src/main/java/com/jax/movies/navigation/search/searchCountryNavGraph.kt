package com.jax.movies.navigation.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.navigation.search.SearchGraph.Companion.COUNTRY_PARAMETER
import com.jax.movies.presentation.search.country.CountryScreen

fun NavGraphBuilder.searchCountryNavGraph(
    navigationState: NavigationState
) {
    composable(SearchGraph.COUNTRY_SCREEN) {
        CountryScreen(
            onClickBack = {
                navigationState.backToSettings()
            },
            onChooseType = {
                navigationState.navHostController.previousBackStackEntry?.savedStateHandle?.set(
                    COUNTRY_PARAMETER,
                    it
                )
            }
        )
    }
}
