package com.jax.movies.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jax.movies.presentation.main.OnBoardingScreen


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RootNavGraph() {
    val navigationState = rememberNavigationState()
    NavHost(
        navController = navigationState.navHostController,
        route = GRAPH.ROOT,
        startDestination = GRAPH.ON_BOARDING_SCREEN,
    ) {
        composable(route = GRAPH.ON_BOARDING_SCREEN) {
            OnBoardingScreen(onFinish = {
                navigationState.navigateTo(GRAPH.MAIN_GRAPH)
            })
        }

        composable(route = GRAPH.MAIN_GRAPH) {
            Test()
        }
    }
}

object GRAPH {
    const val ROOT = "root"
    const val ON_BOARDING_SCREEN = "onBoarding"
    const val MAIN_GRAPH = "main"
    const val DETAILS_GRAPH = "details"
}
