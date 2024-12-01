package com.jax.movies.presentation.search.genre

import androidx.compose.runtime.Composable
import com.jax.movies.presentation.components.SortTypeSection
import com.jax.movies.utils.DefaultLists.defaultGenres

@Composable
fun GenreScreen(
    onClickBack: () -> Unit,
    onChooseType: (String) -> Unit
) {
    SortTypeSection(
        types = defaultGenres,
        topBarTitle = "Жанр",
        searchBarTitle = "Введите жанр",
        onChooseType = onChooseType,
        onClickBack = onClickBack,
    )
}
