package com.jax.movies.navigation.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.presentation.home.HomePage
import com.jax.movies.presentation.home.HomeScreenIntent
import com.jax.movies.presentation.home.HomeViewModel

fun NavGraphBuilder.homeNavGraph(
    navigationState: NavigationState,
    paddingValues: PaddingValues
) {
    composable(BottomScreenItem.HomeScreen.route) {
        val homeViewModel: HomeViewModel = viewModel()
        val homeScreenIntent =
            homeViewModel.homeNavigationChannel.collectAsStateWithLifecycle(HomeScreenIntent.Default)
        LaunchedEffect(homeScreenIntent.value) {
            when (val currentState = homeScreenIntent.value) {
                is HomeScreenIntent.Default -> {}
                is HomeScreenIntent.OnMovieClick -> {
                    navigationState.navigateToMovieDetailScreen(
                        movie = currentState.movie,
                        backRoute = BottomScreenItem.HomeScreen.route
                    )
                }

                is HomeScreenIntent.OnMovieTypeClick -> {
                    navigationState.navigateToMoviesScreen(currentState.movieType)
                }
            }
        }
        HomePage(paddingValues = paddingValues, homeViewModel = homeViewModel)
    }
}