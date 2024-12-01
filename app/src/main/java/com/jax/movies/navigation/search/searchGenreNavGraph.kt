package com.jax.movies.navigation.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.navigation.search.SearchGraph.Companion.GENRE_PARAMETER
import com.jax.movies.presentation.search.genre.GenreScreen

fun NavGraphBuilder.searchGenreNavGraph(
    navigationState: NavigationState
) {
    composable(SearchGraph.GENRE_SCREEN){
        GenreScreen(
            onClickBack = {
                navigationState.backToSettings()
            },
            onChooseType = {
                navigationState.navHostController.previousBackStackEntry?.savedStateHandle?.set(
                    GENRE_PARAMETER,
                    it
                )
            }
        )
    }
}