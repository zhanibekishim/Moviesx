package com.jax.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jax.movies.presentation.home.MainPages

@Composable
fun RootNavGraph() {
    val navigationState = rememberNavigationState()
    NavHost(
        navController = navigationState.navHostController,
        route = GRAPH.ROOT,
        startDestination = GRAPH.ON_BOARDING_SCREEN,
    ) {
        onBoardingScreen(navigationState)
        composable(route = GRAPH.MAIN_GRAPH) {
            MainPages()
        }
    }
}

object GRAPH {
    const val ROOT = "root"
    const val ON_BOARDING_SCREEN = "onBoarding"
    const val MAIN_GRAPH = "main"
    const val DETAILS_GRAPH = "details"
}
