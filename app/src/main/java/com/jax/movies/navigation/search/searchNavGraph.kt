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
import com.jax.movies.navigation.main.BottomScreenItem
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.navigation.search.SearchGraph.Companion.COUNTRY_PARAMETER
import com.jax.movies.navigation.search.SearchGraph.Companion.DEFAULT_PERIOD
import com.jax.movies.navigation.search.SearchGraph.Companion.DEFAULT_RATING
import com.jax.movies.navigation.search.SearchGraph.Companion.DEFAULT_SHOW
import com.jax.movies.navigation.search.SearchGraph.Companion.DEFAULT_SORT
import com.jax.movies.navigation.search.SearchGraph.Companion.GENRE_PARAMETER
import com.jax.movies.navigation.search.SearchGraph.Companion.PERIOD_PARAMETER
import com.jax.movies.navigation.search.SearchGraph.Companion.RATING_PARAMETER
import com.jax.movies.navigation.search.SearchGraph.Companion.SHOW_PARAMETER
import com.jax.movies.navigation.search.SearchGraph.Companion.SORT_PARAMETER
import com.jax.movies.presentation.search.setting.SharedSearchViewModel
import com.jax.movies.presentation.search.country.CountryScreenState.Companion.defaultCountry
import com.jax.movies.presentation.search.genre.GenreScreenState.Companion.defaultGenre
import com.jax.movies.presentation.search.main.SearchScreen
import com.jax.movies.presentation.search.main.SearchScreenIntent
import com.jax.movies.presentation.search.main.SearchViewModel
import com.jax.movies.presentation.search.main.SortBy
import com.jax.movies.presentation.search.setting.ShowType
import com.jax.movies.presentation.search.setting.SortingType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun NavGraphBuilder.searchNavGraph(
    navigationState: NavigationState
) {
    composable(SearchGraph.SearchMain.route) {
        val searchViewModel: SearchViewModel = hiltViewModel<SearchViewModel>()
        val channel = searchViewModel.navigationChannel.collectAsStateWithLifecycle(
            initialValue = SearchScreenIntent.Event.Default
        )

        LaunchedEffect(channel.value) {
            when (val currentEvent = channel.value) {
                is SearchScreenIntent.Event.Default -> {}
                is SearchScreenIntent.Event.OnFilterClick -> {
                    navigationState.navigateToSettingScreen()
                }

                is SearchScreenIntent.Event.OnHomeClick -> {
                    navigationState.navigateTo(BottomScreenItem.HomeScreen.route)
                }

                is SearchScreenIntent.Event.OnMovieClick -> {
                    navigationState.navigateToMovieDetailScreen(currentEvent.movie)
                }

                is SearchScreenIntent.Event.OnProfileClick -> {
                    navigationState.navigateTo(BottomScreenItem.ProfileScreen.route)
                }
            }
        }

        val chosenCountry = navigationState.country()
        val chosenGenre = navigationState.genre()
        val chosenPeriod = navigationState.period()?.collectAsState()
        val chosenRating = navigationState.rating()?.collectAsState()

        val sharedSearchViewModel = SharedSearchViewModel

        Log.d("dasdasdsadasShared1",chosenRating?.value.toString())
        Log.d("dasdasdsadasShared2",sharedSearchViewModel.selectedRating.toString())
        val currentCountry =
            chosenCountry ?: sharedSearchViewModel.selectedCountry ?: defaultCountry
        val currentGenre = chosenGenre ?: sharedSearchViewModel.selectedGenre ?: defaultGenre
        val currentPeriod =
            chosenPeriod?.value ?: sharedSearchViewModel.selectedPeriod ?: DEFAULT_PERIOD
        val currentRating =
            sharedSearchViewModel.selectedRating ?: chosenRating?.value ?: DEFAULT_RATING

        val sortBy = SortBy(
            country = currentCountry,
            genre = currentGenre,
            period = currentPeriod.period(),
            rating = currentRating,
            showType = sharedSearchViewModel.selectedShowType?:DEFAULT_SHOW,
            sort = sharedSearchViewModel.selectedSortType?:DEFAULT_SORT
        )

        SearchScreen(
            searchViewModel = searchViewModel,
            currentRoute = navigationState.currentRoute(),
            sortBy = sortBy
        )
    }
}


fun NavigationState.currentRoute(): String {
    return this.navHostController.currentBackStackEntry?.destination?.route.toString()
}

fun NavigationState.country(): Country? {
    val countryJson = this.navHostController.currentBackStackEntry?.savedStateHandle?.get<String>(
        COUNTRY_PARAMETER
    )

    return try {
        Gson().fromJson(countryJson, Country::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun NavigationState.genre(): Genre? {
    val genreJson = this.navHostController.currentBackStackEntry?.savedStateHandle?.get<String>(
        GENRE_PARAMETER
    )

    return try {
        Gson().fromJson(genreJson, Genre::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun NavigationState.period(): StateFlow<String>? {
    return this.navHostController.currentBackStackEntry?.savedStateHandle?.getStateFlow(
        PERIOD_PARAMETER,
        DEFAULT_PERIOD
    )
}

fun NavigationState.rating(): StateFlow<Double>? {
    return this.navHostController.currentBackStackEntry?.savedStateHandle?.getStateFlow(
        RATING_PARAMETER,
        DEFAULT_RATING
    )
}

fun NavigationState.showType(): StateFlow<ShowType> {
    val json =
        this.navHostController.currentBackStackEntry?.savedStateHandle?.get<String>(SHOW_PARAMETER)
    return if (json != null) {
        val showType = Gson().fromJson(json, ShowType::class.java)
        MutableStateFlow(showType)
    } else {
        MutableStateFlow(DEFAULT_SHOW)
    }
}


fun NavigationState.sortType(): StateFlow<SortingType>? {
    val json =
        this.navHostController.currentBackStackEntry?.savedStateHandle?.get<String>(SORT_PARAMETER)
    return if (json != null) {
        val sortType = Gson().fromJson(json, SortingType::class.java)
        MutableStateFlow(sortType)
    } else {
        MutableStateFlow(DEFAULT_SORT)
    }
}


private fun String.period(): Pair<Int, Int> {
    val first = this.split("-")[0].toInt()
    val second = this.split("-")[1].toInt()
    return first to second
}




















