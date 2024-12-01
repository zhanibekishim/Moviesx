package com.jax.movies.presentation.search.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jax.movies.R
import com.jax.movies.presentation.components.MyTopAppBar
import com.jax.movies.presentation.search.main.FilterSection
import com.jax.movies.presentation.search.main.FilterTypesSection
import com.jax.movies.utils.DefaultLists.defaultMovieTypes

@Composable
fun SearchSettingScreen(
    searchSettingViewModel: SearchSettingViewModel,
    country: String,
    genre: String,
    period: String
) {
    val state by searchSettingViewModel.screenState.collectAsStateWithLifecycle()
    FilterScreenContent(
        state = state,
        onBackClick = {
            searchSettingViewModel.handleEvent(SearchSettingScreenIntent.Event.OnBackClick)
        },
        onFilterTypeChoose = {
            when (it) {
                FilterType.COUNTRY -> {
                    searchSettingViewModel.handleEvent(SearchSettingScreenIntent.Event.OnCountryClick)
                }

                FilterType.GENRE -> {
                    searchSettingViewModel.handleEvent(SearchSettingScreenIntent.Event.OnGenreClick)
                }

                FilterType.YEAR -> {
                    searchSettingViewModel.handleEvent(SearchSettingScreenIntent.Event.OnPeriodClick)
                }

                FilterType.RATING -> {

                }
            }
        },
        onShowTypeChoose = {},
        onSortingTypeChoose = {}
    )
    LaunchedEffect(Unit) {
        searchSettingViewModel.handleIntent(SearchSettingScreenIntent.OnCountryChange(country))
        searchSettingViewModel.handleIntent(SearchSettingScreenIntent.OnPeriodChange(period))
        searchSettingViewModel.handleIntent(SearchSettingScreenIntent.OnGenreChange(genre))
    }
}

@Composable
private fun FilterScreenContent(
    onBackClick: () -> Unit,
    onShowTypeChoose: (ShowType) -> Unit,
    onSortingTypeChoose: (SortingType) -> Unit,
    onFilterTypeChoose: (FilterType) -> Unit,
    state: SearchSettingScreenState
) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                onNavClick = onBackClick,
                navIcon = R.drawable.icon_back,
                title = "Настройки поиска"
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            FilterSection(
                title = stringResource(R.string.show),
                types = defaultMovieTypes,
                onChoose = {
                    val showType = it as ShowType
                    onShowTypeChoose(showType)
                },
                chosenType = state.showType
            )
            FilterTypesSection(
                onChoose = onFilterTypeChoose
            )
            FilterSection(
                title = stringResource(R.string.sort),
                types = defaultMovieTypes,
                onChoose = {
                    val sortingType = it as SortingType
                    onSortingTypeChoose(sortingType)
                },
                chosenType = state.sortingType
            )
        }
    }
}