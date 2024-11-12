package com.jax.movies.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jax.movies.presentation.home.BottomScreenItem
import com.jax.movies.presentation.home.HomePage
import com.jax.movies.presentation.profile.ProfileScreen
import com.jax.movies.presentation.search.SearchScreen

@Composable
fun MainNavGraph(
    navigationState: NavigationState,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navigationState.navHostController,
        route = GRAPH.MAIN_GRAPH,
        startDestination = BottomScreenItem.HomeScreen.route
    ) {
        composable(BottomScreenItem.HomeScreen.route) {
            HomePage(
                paddingValues = paddingValues,
                onListClick = { navigationState.navigateToMovies(it) },
                onMovieClick = { navigationState.navigateToMovie(it) }
            )
        }
        composable(BottomScreenItem.SearchScreen.route) { SearchScreen(paddingValues) }
        composable(BottomScreenItem.ProfileScreen.route) { ProfileScreen(paddingValues) }
        detailsScreen(navigationState)
    }
}
