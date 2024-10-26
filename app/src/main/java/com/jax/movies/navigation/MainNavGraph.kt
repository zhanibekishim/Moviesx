package com.jax.movies.navigation

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jax.movies.presentation.main.BottomScreenItem
import com.jax.movies.presentation.main.HomePage
import com.jax.movies.presentation.detail.MovieContent
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
                onMovieClick = { id, type ->
                    navigationState.navigateToMovie(id = id, type = type)
                }
            )
        }
        composable(BottomScreenItem.SearchScreen.route) { SearchScreen(paddingValues) }
        composable(BottomScreenItem.ProfileScreen.route) { ProfileScreen(paddingValues) }
        detailsScreen()
    }

}

fun NavGraphBuilder.detailsScreen() {
    navigation(
        route = GRAPH.DETAILS_GRAPH,
        startDestination = Details.Movie.route
    ) {
        composable(Details.Movie.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
                ?: throw IllegalStateException("ID is missing")
            val type = backStackEntry.arguments?.getString("type")
                ?: throw IllegalStateException("Type is missing")

            MovieContent(movieId = id.toLong(), movieType = type)
        }
    }
}


sealed class Details(val route: String) {
    data object Movie : Details("Movie/{id}/{type}") {
        fun getRouteWithArgs(id: Long, type: String): String {
            return "Movie/$id/${Uri.encode(type)}"
        }
    }
}
/*fun NavGraphBuilder.homeScreenGraph() {
   navigation(route = "", startDestination = HomePage.Movies.route){
       composable(HomePage.Movies.route) {
           HomePage()
       }
       composable(HomePage.Detail.route) {
           Movie()
       }
   }
}*/

/*
sealed class HomePage(val route: String) {
    data object Movies:Details("Movies")
    data object Detail : Details("Detail")
}
*/


