package com.jax.movies.navigation.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jax.movies.navigation.detail.actorNavGraph
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.navigation.detail.detailsScreens
import com.jax.movies.navigation.detail.filmographyNavGraph
import com.jax.movies.navigation.detail.galleryNavGraph
import com.jax.movies.navigation.detail.movieDetailGraph
import com.jax.movies.navigation.detail.moviesCollectionNavGraph
import com.jax.movies.navigation.root.rememberNavigationState
import com.jax.movies.navigation.root.GRAPH
import com.jax.movies.presentation.components.BottomNavigationBar
import com.jax.movies.presentation.profile.ProfileScreen
import com.jax.movies.presentation.search.SearchScreen

@Composable
private fun MainNavGraph(
    navigationState: NavigationState,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navigationState.navHostController,
        route = GRAPH.MAIN_GRAPH,
        startDestination = BottomScreenItem.HomeScreen.route
    ) {
        homeNavGraph(navigationState = navigationState, paddingValues = paddingValues)
        composable(BottomScreenItem.SearchScreen.route) { SearchScreen(paddingValues) }
        composable(BottomScreenItem.ProfileScreen.route) { ProfileScreen(paddingValues) }
        detailsScreens(navigationState)
    }
}
















@Composable
fun MainPages() {
    val navigationState = rememberNavigationState()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = navigationState.navHostController.currentBackStackEntry?.destination?.route.toString(),
                onHomeClick = {
                    navigationState.navigateTo(BottomScreenItem.HomeScreen.route)
                },
                onProfileClick = {
                    navigationState.navigateTo(BottomScreenItem.ProfileScreen.route)
                },
                onSearchClick = {
                    navigationState.navigateTo(BottomScreenItem.SearchScreen.route)
                },
            )
        }
    ) { paddingValues ->
        MainNavGraph(
            navigationState = navigationState,
            paddingValues = paddingValues
        )
    }
}