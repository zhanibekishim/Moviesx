package com.jax.movies.navigation.search

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jax.movies.navigation.main.BottomScreenItem
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.navigation.search.SearchGraph.Companion.COUNTRY_PARAMETER
import com.jax.movies.navigation.search.SearchGraph.Companion.DEFAULT_COUNTRY
import com.jax.movies.navigation.search.SearchGraph.Companion.DEFAULT_GENRE
import com.jax.movies.navigation.search.SearchGraph.Companion.DEFAULT_PERIOD
import com.jax.movies.navigation.search.SearchGraph.Companion.GENRE_PARAMETER
import com.jax.movies.navigation.search.SearchGraph.Companion.PERIOD_PARAMETER
import com.jax.movies.presentation.search.main.SearchScreen
import com.jax.movies.presentation.search.main.SearchScreenIntent
import com.jax.movies.presentation.search.main.SearchViewModel
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
                    navigationState.navigateTo(SearchGraph.SearchSetting.route)
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

        val chosenCountry = navigationState.country()?.collectAsState()
        val chosenGenre = navigationState.genre()?.collectAsState()
        val chosenPeriod = navigationState.period()?.collectAsState()
        SearchScreen(
            searchViewModel = searchViewModel,
            currentRoute = navigationState.currentRoute()
        )
    }
}

private fun NavigationState.currentRoute(): String {
    return this.navHostController.currentBackStackEntry?.destination?.route.toString()
}
fun NavigationState.country(): StateFlow<String>? {
    return this.navHostController.currentBackStackEntry?.savedStateHandle?.getStateFlow(COUNTRY_PARAMETER,DEFAULT_COUNTRY)
}
fun NavigationState.genre(): StateFlow<String>? {
    return this.navHostController.currentBackStackEntry?.savedStateHandle?.getStateFlow(GENRE_PARAMETER,DEFAULT_GENRE)
}
fun NavigationState.period(): StateFlow<String>? {
    return this.navHostController.currentBackStackEntry?.savedStateHandle?.getStateFlow(PERIOD_PARAMETER,DEFAULT_PERIOD)
}
























