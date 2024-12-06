package com.jax.movies.navigation.search

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.jax.movies.domain.entity.home.Country
import com.jax.movies.domain.entity.home.Genre
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.navigation.search.SearchGraph.Companion.COUNTRY_PARAMETER
import com.jax.movies.navigation.search.SearchGraph.Companion.DEFAULT_PERIOD
import com.jax.movies.navigation.search.SearchGraph.Companion.GENRE_PARAMETER
import com.jax.movies.navigation.search.SearchGraph.Companion.PERIOD_PARAMETER
import com.jax.movies.presentation.search.setting.SharedSearchViewModel
import com.jax.movies.presentation.search.country.CountryScreenState.Companion.defaultCountry
import com.jax.movies.presentation.search.genre.GenreScreenState.Companion.defaultGenre
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
                    navigationState.navigateToCountryScreen()
                }

                SearchSettingScreenIntent.Event.OnGenreClick -> {
                    navigationState.navigateToGenreScreen()
                }

                SearchSettingScreenIntent.Event.OnPeriodClick -> {
                    navigationState.navigateToPeriodScreen()
                }
            }
        }

        val sharedSearchViewModel = SharedSearchViewModel

        val countryState = navigationState.country()
        val genreState = navigationState.genre()
        val periodState = navigationState.period()?.collectAsState()

        Log.d("collected", "ChooseTypeSection: $countryState")
        Log.d("collected", "ChooseTypeSection: $genreState")
        Log.d("collected", "ChooseTypeSection: $periodState")

        val country = countryState ?: (sharedSearchViewModel.selectedCountry ?: defaultCountry)
        val genre = genreState ?: (sharedSearchViewModel.selectedGenre ?: defaultGenre)
        val period = periodState?.value ?: (sharedSearchViewModel.selectedPeriod ?: DEFAULT_PERIOD)

        sharedSearchViewModel.setGenre(genre)
        sharedSearchViewModel.setCountry(country)
        sharedSearchViewModel.setPeriod(period)

        navigationState.setDataToBackScreen(country, genre, period)

        SearchSettingScreen(
            searchSettingViewModel = searchSettingViewModel,
            country = country,
            genre = genre,
            period = period
        )
    }
}

private fun NavigationState.setDataToBackScreen(
    country: Country,
    genre: Genre,
    period: String,
  /*  rating: Double,*/
   /* showType: ShowType,
    sortType: SortingType*/
) {
    this.navHostController.previousBackStackEntry?.savedStateHandle?.set(
        COUNTRY_PARAMETER,
        Gson().toJson(country)
    )
    this.navHostController.previousBackStackEntry?.savedStateHandle?.set(
        GENRE_PARAMETER,
        Gson().toJson(genre)
    )
    this.navHostController.previousBackStackEntry?.savedStateHandle?.set(
        PERIOD_PARAMETER,
        period
    )
  /*  this.navHostController.previousBackStackEntry?.savedStateHandle?.set(
        RATING_PARAMETER,
        rating
    )*/
  /*  this.navHostController.previousBackStackEntry?.savedStateHandle?.set(
        SHOW_PARAMETER,
        Gson().toJson(showType)
    )
    this.navHostController.previousBackStackEntry?.savedStateHandle?.set(
        SORT_PARAMETER,
        Gson().toJson(sortType)
    )*/
}
