package com.jax.movies.presentation.search.country

import androidx.compose.runtime.Composable
import com.jax.movies.presentation.components.SortTypeSection
import com.jax.movies.utils.DefaultLists.defaultCountries

@Composable
fun CountryScreen(
    onClickBack: () -> Unit,
    onChooseType: (String) -> Unit,
) {
    SortTypeSection(
        topBarTitle = "Страна",
        searchBarTitle = "Введите страну",
        onClickBack = onClickBack,
        onChooseType = onChooseType,
        types = defaultCountries
    )
}