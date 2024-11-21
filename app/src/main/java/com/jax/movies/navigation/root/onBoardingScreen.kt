package com.jax.movies.navigation.root

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jax.movies.presentation.onboarding.OnBoardingScreen

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.onBoardingScreen(
    navigationState: NavigationState
){
    composable(route = GRAPH.ON_BOARDING_SCREEN) {
        OnBoardingScreen(onFinish = {
            navigationState.navigateTo(GRAPH.MAIN_GRAPH)
        })
    }
}