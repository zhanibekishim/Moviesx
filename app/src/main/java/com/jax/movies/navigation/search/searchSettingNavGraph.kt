package com.jax.movies.navigation.search

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.navigation.search.SearchGraph.Companion.DEFAULT_COUNTRY
import com.jax.movies.navigation.search.SearchGraph.Companion.DEFAULT_GENRE
import com.jax.movies.navigation.search.SearchGraph.Companion.DEFAULT_PERIOD
import com.jax.movies.presentation.search.setting.SearchSettingScreen
import com.jax.movies.presentation.search.setting.SearchSettingScreenIntent
import com.jax.movies.presentation.search.setting.SearchSettingViewModel

fun NavGraphBuilder.searchSettingNavGraph(
    navigationState: NavigationState
) {
    composable(SearchGraph.SETTING_SCREEN) {
        val searchSettingViewModel = hiltViewModel<SearchSettingViewModel>()
        val channel = searchSettingViewModel.navigationChannel
            .collectAsStateWithLifecycle(SearchSettingScreenIntent.Event.Default)
        LaunchedEffect(channel.value) {
            when (channel.value) {
                SearchSettingScreenIntent.Event.Default -> {}
                SearchSettingScreenIntent.Event.OnBackClick -> {
                    navigationState.navHostController.popBackStack()
                }

                SearchSettingScreenIntent.Event.OnCountryClick -> {
                    navigationState.navigateTo(SearchGraph.COUNTRY_SCREEN)
                }

                SearchSettingScreenIntent.Event.OnGenreClick -> {
                    navigationState.navigateTo(SearchGraph.GENRE_SCREEN)
                }

                SearchSettingScreenIntent.Event.OnPeriodClick -> {
                    navigationState.navigateTo(SearchGraph.PERIOD_SCREEN)
                }
            }
        }
        val chosenCountry = navigationState.country()?.collectAsState()
        val chosenGenre = navigationState.genre()?.collectAsState()
        val chosenPeriod = navigationState.period()?.collectAsState()
        SearchSettingScreen(
            searchSettingViewModel = searchSettingViewModel,
            country = chosenCountry?.value ?: DEFAULT_COUNTRY,
            genre = chosenGenre?.value ?: DEFAULT_GENRE,
            period = chosenPeriod?.value ?: DEFAULT_PERIOD
        )
    }
}