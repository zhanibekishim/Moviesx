package com.jax.movies.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.home.MoviesType

class NavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            if (route == GRAPH.MAIN_GRAPH) {
                popUpTo(GRAPH.ON_BOARDING_SCREEN) {
                    inclusive = true
                }
            } else {
                popUpTo(navHostController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }


    fun navigateToMovie(movie: Movie) {
        navHostController.navigate(Details.MovieScreen.getRouteWithArgs(movie)) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
        }
    }
    fun navigateToMovies(type: MoviesType) {
        navHostController.navigate(Details.MoviesScreen.getRouteWithArgs(type)) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
        }
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}

