package com.jax.movies.navigation.detail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jax.movies.navigation.root.GRAPH
import com.jax.movies.navigation.root.NavigationState

fun NavGraphBuilder.detailsScreens(
    navigationState: NavigationState
) {
    navigation(
        route = GRAPH.DETAILS_GRAPH,
        startDestination = Details.MovieScreen.route
    ) {
        movieDetailGraph(navigationState)
        moviesCollectionNavGraph(navigationState)
        actorNavGraph(navigationState)
        filmographyNavGraph(navigationState)
        galleryNavGraph(navigationState)
        filmographyNavGraph(navigationState)
    }
}
