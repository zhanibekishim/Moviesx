package com.jax.movies.navigation.main

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.navigation.search.SearchGraph
import com.jax.movies.presentation.profile.ProfileScreen
import com.jax.movies.presentation.profile.ProfileScreenIntent
import com.jax.movies.presentation.profile.ProfileScreenViewModel

fun NavGraphBuilder.profileNavGraph(
    navigationState: NavigationState
) {
    composable(BottomScreenItem.ProfileScreen.route) {
        Log.d("dasdasdasdasdsadas", "ProfileNavGraph: Profile")
        val profileScreenViewModel: ProfileScreenViewModel = hiltViewModel<ProfileScreenViewModel>()
        val currentRoute =
            navigationState.navHostController.currentBackStackEntry?.destination?.route.toString()
        val channel = profileScreenViewModel.profileNavigationChannel.collectAsStateWithLifecycle(
            ProfileScreenIntent.Event.Default
        )
        LaunchedEffect(channel.value){
            when (val currentIntent = channel.value) {
                ProfileScreenIntent.Event.Default -> {}
                ProfileScreenIntent.Event.OnHomeClick -> {
                    navigationState.navigateTo(BottomScreenItem.HomeScreen.route)
                }

                ProfileScreenIntent.Event.OnSearchClick -> {
                    navigationState.navigateTo(SearchGraph.SearchMain.route)
                }

                is ProfileScreenIntent.Event.OnMovieClick -> {
                    navigationState.navigateToMovieDetailScreen(currentIntent.movie)
                }

                is ProfileScreenIntent.Event.OnDeleteClick -> {}
            }
        }
        ProfileScreen(
            profileScreenViewModel = profileScreenViewModel,
            currentRoute = currentRoute
        )
    }
}