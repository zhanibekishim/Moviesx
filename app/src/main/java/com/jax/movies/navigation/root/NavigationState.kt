package com.jax.movies.navigation.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.entity.home.MoviesType
import com.jax.movies.navigation.detail.Details
import com.jax.movies.navigation.search.SearchGraph

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
            /*if(backRoute != Details.MovieScreen.route){
                if (backRoute != null) {
                    popUpTo(backRoute) {
                        saveState = true
                    }
                }
            }
            if(backRoute != null) restoreState = true
            launchSingleTop = true*/
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

    fun navigateToCountryScreen() {
        navHostController.navigate(SearchGraph.COUNTRY_SCREEN) {
            /*popUpTo(SearchGraph.SearchSetting.route) {
                saveState = true
            }*/
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToGenreScreen() {
        navHostController.navigate(SearchGraph.GENRE_SCREEN) {
            /*popUpTo(SearchGraph.SearchSetting.route) {
                saveState = true
            }*/
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToPeriodScreen() {
        navHostController.navigate(SearchGraph.PERIOD_SCREEN) {
            /*popUpTo(SearchGraph.SearchSetting.route) {
                saveState = true
            }*/
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToSettingScreen() {
        navHostController.navigate(SearchGraph.SETTING_SCREEN) {
            /*popUpTo(SearchGraph.SearchMain.route) {
                saveState = true
            }*/
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

