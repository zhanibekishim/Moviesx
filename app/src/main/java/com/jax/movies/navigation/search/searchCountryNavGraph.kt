package com.jax.movies.navigation.search

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.navigation.search.SearchGraph.Companion.COUNTRY_PARAMETER
import com.jax.movies.presentation.search.country.CountryScreen
import com.jax.movies.presentation.search.country.CountryScreenIntent
import com.jax.movies.presentation.search.country.CountryViewModel

fun NavGraphBuilder.searchCountryNavGraph(
    navigationState: NavigationState
) {
    composable(SearchGraph.COUNTRY_SCREEN) {
        val countryViewModel = hiltViewModel<CountryViewModel>()
        val navState = countryViewModel.countryNavigation.collectAsStateWithLifecycle(
            CountryScreenIntent.Event.Default
        )
        LaunchedEffect(navState.value) {
            when(val currentState = navState.value){
                is CountryScreenIntent.Event.Default -> {}
                is CountryScreenIntent.Event.OnClickBack -> {
                    navigationState.navHostController.popBackStack()
                }
                is CountryScreenIntent.Event.OnCountryClick -> {
                    Log.d("clicked", "ChooseTypeSection: $it")
                    navigationState.navHostController.previousBackStackEntry?.savedStateHandle?.set(
                        COUNTRY_PARAMETER,
                        Gson().toJson(currentState.country)
                    )
                }
            }
        }
        CountryScreen(countryViewModel)
    }
}


