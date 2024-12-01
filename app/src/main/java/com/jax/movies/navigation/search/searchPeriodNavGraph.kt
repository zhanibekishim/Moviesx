package com.jax.movies.navigation.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.navigation.search.SearchGraph.Companion.PERIOD_PARAMETER
import com.jax.movies.presentation.search.period.PeriodScreen

fun NavGraphBuilder.searchPeriodNavGraph(
    navigationState: NavigationState
) {
    composable(SearchGraph.PERIOD_SCREEN){
        PeriodScreen(
            onClickBack = {
                navigationState.backToSettings()
            },
            onChoosePeriod = {from,to->
                navigationState.navHostController.previousBackStackEntry?.savedStateHandle?.set(
                    PERIOD_PARAMETER,
                    from
                )
            }
        )
    }
}