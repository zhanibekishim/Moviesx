package com.jax.movies.presentation.search.country

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jax.movies.R
import com.jax.movies.domain.entity.home.Country
import com.jax.movies.presentation.components.CustomSearchBar
import com.jax.movies.presentation.components.SortTypeSection
import com.jax.movies.presentation.search.country.CountryScreenState.Companion.defaultCountries
import com.jax.movies.presentation.search.country.CountryScreenState.Companion.defaultCountry

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CountryScreen(
    countryViewModel: CountryViewModel
) {
    val state = countryViewModel.screenState.collectAsStateWithLifecycle(
        initialValue = CountryScreenState.Initial
    )

    when (val currentState = state.value) {
        is CountryScreenState.Initial -> {
            CountryScreenContent(
                onSearchClick = {
                    Log.d("onSearchClick", "CountryScreenContent: $it")
                    countryViewModel.handleIntent(CountryScreenIntent.OnSearchClick(it))
                },
                onNewQuery = {
                    /*countryViewModel.handleIntent(CountryScreenIntent.OnQueryChange(it))*/
                },
                onClickBack = {
                    countryViewModel.handleEvent(CountryScreenIntent.Event.OnClickBack)
                },
                onChooseType = {
                    countryViewModel.handleEvent(CountryScreenIntent.Event.OnCountryClick(it))
                }
            )
        }

        is CountryScreenState.Loading -> {
            GenreLoadingScreen(
                onSearchClick = {
                    Log.d("onSearchClick", "GenreLoadingScreen: $it")
                    countryViewModel.handleIntent(CountryScreenIntent.OnSearchClick(it))
                },
                onNewQuery = {
                 /*   countryViewModel.handleIntent(CountryScreenIntent.OnQueryChange(it))*/
                }
            )
        }

        is CountryScreenState.Error -> {
            CountryErrorScreen(
                onSearchClick = {
                    Log.d("onSearchClick", "CountryErrorScreen: $it")
                    countryViewModel.handleIntent(CountryScreenIntent.OnSearchClick(it))
                },
                onNewQuery = {
                   /* countryViewModel.handleIntent(CountryScreenIntent.OnQueryChange(it))*/
                }
            )
        }

        is CountryScreenState.Success -> {
            CountryScreenContent(
                onSearchClick = {
                    Log.d("onSearchClick", "CountryScreenContent: $it")
                    countryViewModel.handleIntent(CountryScreenIntent.OnSearchClick(it))
                },
                onNewQuery = {
                  /*  countryViewModel.handleIntent(CountryScreenIntent.OnQueryChange(it))*/
                },
                onClickBack = {
                    countryViewModel.handleEvent(CountryScreenIntent.Event.OnClickBack)
                },
                onChooseType = {
                    countryViewModel.handleEvent(CountryScreenIntent.Event.OnCountryClick(it))
                },
                countries = currentState.countries,
                query = currentState.query
            )
        }
    }

}

@Composable
private fun CountryScreenContent(
    query: String = "",
    countries: Country = defaultCountry,
    onChooseType: (Country) -> Unit,
    onSearchClick: (String) -> Unit,
    onNewQuery: (String) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    SortTypeSection(
        query = query,
        topBarTitle = "Страна",
        searchBarTitle = "Введите страну",
        types = countries,
        onClickBack = onClickBack,
        onChooseType = onChooseType,
        onSearchClick = onSearchClick,
        onNewQuery = onNewQuery,
        modifier = modifier
    )
}

@Composable
private fun CountryErrorScreen(
    onSearchClick: (String) -> Unit,
    onNewQuery: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        CustomSearchBar(
            leadingIcon = R.drawable.icon_search,
            placeHolderText = "Введите страну",
            onSearchClick = onSearchClick,
            onNewQuery = onNewQuery,
            modifier = Modifier.padding(16.dp)
        )
        Text(text = "Не удалось найти",
            modifier = Modifier.padding(16.dp))
    }
}

@Composable
private fun GenreLoadingScreen(
    onSearchClick: (String) -> Unit,
    onNewQuery: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        CustomSearchBar(
            leadingIcon = R.drawable.icon_search,
            placeHolderText = "Введите страну",
            onSearchClick = onSearchClick,
            onNewQuery = onNewQuery,
            modifier = Modifier.padding(16.dp)
        )
        CircularProgressIndicator()
    }
}