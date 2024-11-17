package com.jax.movies.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.entity.home.MoviesType

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


    fun navigateToMovieDetailScreen(movie: Movie, backRoute: String? = null) {
        navHostController.navigate(Details.MovieScreen.getRouteWithArgs(movie)) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            /*  if (backRoute != null) {
                  popUpTo(backRoute){
                      saveState = true
                  }
              }*/
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToMoviesScreen(type: MoviesType) {
        navHostController.navigate(Details.MoviesScreen.getRouteWithArgs(type)) {
            popUpTo(Details.MovieScreen.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToActorScreen(actor: Actor) {
        navHostController.navigate(Details.ActorsScreen.getRouteWithArgs(actor)) {
            popUpTo(Details.MoviesScreen.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToGalleryScreen(movie: Movie) {
        navHostController.navigate(Details.GalleryScreen.getRouteWithArgs(movie)) {
            popUpTo(Details.ActorsScreen.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToFilmographyScreen(actor: Actor) {
        navHostController.navigate(Details.Filmography.getRouteWithArgs(actor)) {
            popUpTo(Details.ActorsScreen.route) {
                saveState = true
            }
            launchSingleTop = true
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

