package com.jax.movies.navigation.root

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jax.movies.navigation.main.MainNavGraph

@Composable
fun RootNavGraph() {
    val navigationState = rememberNavigationState()
    NavHost(
        navController = navigationState.navHostController,
        route = GRAPH.ROOT,
        startDestination = GRAPH.ON_BOARDING_SCREEN,
    ) {
        onBoardingScreen(navigationState)
        composable(route = GRAPH.MAIN_GRAPH) { MainNavGraph() }
    }
}

