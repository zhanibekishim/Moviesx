package com.jax.movies.presentation.search.setting

import android.util.Log
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
import com.jax.movies.domain.entity.home.Country
import com.jax.movies.domain.entity.home.Genre
import com.jax.movies.presentation.components.FilterSection
import com.jax.movies.presentation.components.MyTopAppBar

import com.jax.movies.presentation.search.main.FilterTypesSection
import com.jax.movies.utils.DefaultLists.defaultShowType
import com.jax.movies.utils.DefaultLists.defaultSortingType

@Composable
fun SearchSettingScreen(
    searchSettingViewModel: SearchSettingViewModel,
    country: Country,
    genre: Genre,
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
                is FilterType.Country -> {
                    searchSettingViewModel.handleEvent(SearchSettingScreenIntent.Event.OnCountryClick)
                }

                is FilterType.Genre -> {
                    searchSettingViewModel.handleEvent(SearchSettingScreenIntent.Event.OnGenreClick)
                }

                is FilterType.Period -> {
                    searchSettingViewModel.handleEvent(SearchSettingScreenIntent.Event.OnPeriodClick)
                }
                is FilterType.Rating -> {

                }
            }
        },
        onShowTypeChoose = {
            searchSettingViewModel.handleIntent(SearchSettingScreenIntent.OnShowTypeChoose(it))
        },
        onSortingTypeChoose = {
            searchSettingViewModel.handleIntent(SearchSettingScreenIntent.OnSortingTypeChoose(it))
        },
        onRatingChoose = {
            searchSettingViewModel.handleIntent(SearchSettingScreenIntent.OnRatingChange(it))
        },
        filterTypes = toFilterTypeList(country = country, genre = genre, period = period)
    )
    LaunchedEffect(country, genre, period) {
        Log.d("LaunchedEffect", "Params: country=$country, genre=$genre, period=$period")
        searchSettingViewModel.handleIntent(SearchSettingScreenIntent.OnCountryChange(country))
        searchSettingViewModel.handleIntent(
            SearchSettingScreenIntent.OnPeriodChange(
                periodFrom = period.split("-")[0].toInt(),
                periodTo = period.split("-")[1].toInt()
            )
        )
        searchSettingViewModel.handleIntent(SearchSettingScreenIntent.OnGenreChange(genre))
    }

}

@Composable
private fun FilterScreenContent(
    onBackClick: () -> Unit,
    onRatingChoose: (Double) -> Unit,
    onShowTypeChoose: (ShowType) -> Unit,
    onSortingTypeChoose: (SortingType) -> Unit,
    onFilterTypeChoose: (FilterType) -> Unit,
    filterTypes: List<FilterType>,
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
                types = defaultShowType,
                onChoose = {
                    val showType = it
                    onShowTypeChoose(showType)
                },
                chosenType = state.showType
            )
            FilterTypesSection(
                onChoose = onFilterTypeChoose,
                types = filterTypes,
                onRatingChoose = onRatingChoose
            )
            FilterSection(
                title = stringResource(R.string.sort),
                types = defaultSortingType,
                onChoose = {
                    val sortingType = it
                    onSortingTypeChoose(sortingType)
                },
                chosenType = state.sortingType
            )
        }
    }
}

private fun toFilterTypeList(
    country: Country,
    genre: Genre,
    period: String,
    rating: Double = 5.0
): List<FilterType> {
    val countryFT = FilterType.Country(country.name)
    val genreFT = FilterType.Genre(genre.name)
    val periodFT = FilterType.Period(
        from = period.split("-")[0].toInt(),
        to = period.split("-")[1].toInt()
    )
    val ratingFT = FilterType.Rating(rating)
    return listOf(countryFT, genreFT, periodFT,ratingFT)
}