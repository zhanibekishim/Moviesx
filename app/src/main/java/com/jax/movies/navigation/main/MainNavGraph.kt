package com.jax.movies.navigation.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.jax.movies.navigation.detail.detailsScreens
import com.jax.movies.navigation.root.GRAPH
import com.jax.movies.navigation.root.rememberNavigationState
import com.jax.movies.navigation.search.searchCountryNavGraph
import com.jax.movies.navigation.search.searchGenreNavGraph
import com.jax.movies.navigation.search.searchNavGraph
import com.jax.movies.navigation.search.searchPeriodNavGraph
import com.jax.movies.navigation.search.searchSettingNavGraph

@Composable
fun MainNavGraph() {
    val navigationState = rememberNavigationState()
    NavHost(
        navController = navigationState.navHostController,
        route = GRAPH.MAIN_GRAPH,
        startDestination = BottomScreenItem.HomeScreen.route
    ) {
        homeNavGraph(navigationState)
        searchNavGraph(navigationState)
        profileNavGraph(navigationState)
        detailsScreens(navigationState)
        searchSettingNavGraph(navigationState)
        searchPeriodNavGraph(navigationState)
        searchGenreNavGraph(navigationState)
        searchCountryNavGraph(navigationState)
    }
}

