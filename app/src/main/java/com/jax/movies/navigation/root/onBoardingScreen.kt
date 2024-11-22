package com.jax.movies.navigation.root

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jax.movies.presentation.onboarding.OnBoardingScreen
import com.jax.movies.presentation.onboarding.OnBoardingScreenIntent
import com.jax.movies.presentation.onboarding.OnBoardingViewModel

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.onBoardingScreen(
    navigationState: NavigationState
) {
    composable(route = GRAPH.ON_BOARDING_SCREEN) {
        val onBoardingViewModel: OnBoardingViewModel = viewModel()
        val currentIntent = onBoardingViewModel.onBoardingNavigationChannel
            .collectAsStateWithLifecycle(OnBoardingScreenIntent.Default)
        LaunchedEffect(currentIntent.value) {
            when (currentIntent.value) {
                OnBoardingScreenIntent.Default -> {}
                OnBoardingScreenIntent.OnFinishClicked -> {
                    navigationState.navigateTo(GRAPH.MAIN_GRAPH)
                }
            }
        }
        OnBoardingScreen(onBoardingViewModel)
    }
}