package com.jax.movies.navigation.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jax.movies.navigation.detail.detailsScreens
import com.jax.movies.navigation.root.GRAPH
import com.jax.movies.navigation.root.rememberNavigationState
import com.jax.movies.presentation.profile.ProfileScreen
import com.jax.movies.presentation.search.SearchScreen

@Composable
fun MainNavGraph() {
    val navigationState = rememberNavigationState()
    NavHost(
        navController = navigationState.navHostController,
        route = GRAPH.MAIN_GRAPH,
        startDestination = BottomScreenItem.HomeScreen.route
    ) {
        homeNavGraph(navigationState)
        composable(BottomScreenItem.SearchScreen.route) { SearchScreen(navigationState) }
        composable(BottomScreenItem.ProfileScreen.route) { ProfileScreen(navigationState) }
        detailsScreens(navigationState)
    }
}

