package com.jax.movies.navigation.detail

import android.os.Build
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.presentation.detail.actor.ActorDetailScreen
import com.jax.movies.presentation.detail.actor.ActorDetailViewModel
import com.jax.movies.presentation.detail.actor.ActorScreenIntent

fun NavGraphBuilder.actorNavGraph(
    navigationState: NavigationState,
) {
    composable(
        route = Details.ActorsScreen.route,
        arguments = listOf(navArgument(Details.ACTOR_PARAMETER) { type = Actor.navType })
    ) { backStackEntry ->

        val actor = backStackEntry.getActor()
        val actorViewModel: ActorDetailViewModel = viewModel()
        val actorDetailIntent =
            actorViewModel.actorNavigationChannel.collectAsStateWithLifecycle(ActorScreenIntent.Default)

        LaunchedEffect(actorDetailIntent.value) {
            when (val currentIntent = actorDetailIntent.value) {
                is ActorScreenIntent.Default -> {}
                is ActorScreenIntent.OnFilmographyClick -> {
                    navigationState.navigateToFilmographyScreen(currentIntent.actor)
                }

                is ActorScreenIntent.OnMovieClick -> {
                    navigationState.navigateToMovieDetailScreen(
                        currentIntent.movie,
                        backRoute = Details.ActorsScreen.getRouteWithArgs(currentIntent.actor)
                    )
                }

                is ActorScreenIntent.OnClickBack -> {
                    navigationState.navHostController.popBackStack()
                }
            }
        }

        ActorDetailScreen(
            actorDetailViewModel = actorViewModel,
            actor = actor
        )
    }
}

private fun NavBackStackEntry.getActor(): Actor {
    return this.arguments?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            it.getParcelable(Details.ACTOR_PARAMETER, Actor::class.java)
        } else {
            @Suppress("DEPRECATION")
            it.getParcelable(Details.ACTOR_PARAMETER)
        }
    } ?: throw IllegalStateException("Actor is missing")
}