package com.jax.movies.navigation.search

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.navigation.search.SearchGraph.Companion.GENRE_PARAMETER
import com.jax.movies.presentation.search.genre.GenreScreen
import com.jax.movies.presentation.search.genre.GenreScreenIntent
import com.jax.movies.presentation.search.genre.GenreViewModel

fun NavGraphBuilder.searchGenreNavGraph(
    navigationState: NavigationState
) {
    composable(SearchGraph.GENRE_SCREEN) {
        val genreViewModel = hiltViewModel<GenreViewModel>()
        val navState = genreViewModel.genresNavigation.collectAsStateWithLifecycle(
            GenreScreenIntent.Event.Default
        )
        LaunchedEffect(navState.value) {
            when (val currentState = navState.value) {
                is GenreScreenIntent.Event.Default -> {}
                is GenreScreenIntent.Event.OnClickBack -> {
                    navigationState.navHostController.popBackStack()
                }
                is GenreScreenIntent.Event.OnGenreClick -> {
                    navigationState.navHostController.previousBackStackEntry?.savedStateHandle?.set(
                        GENRE_PARAMETER,
                        Gson().toJson(currentState.genre)
                    )
                }
            }
        }
        GenreScreen(genreViewModel)
    }
}