package com.jax.movies.presentation.search.genre

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jax.movies.R
import com.jax.movies.domain.entity.home.Genre
import com.jax.movies.presentation.components.CustomSearchBar
import com.jax.movies.presentation.components.SortTypeSection
import com.jax.movies.presentation.search.genre.GenreScreenState.Companion.defaultGenre
import com.jax.movies.presentation.search.genre.GenreScreenState.Companion.defaultGenres

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun GenreScreen(genreViewModel: GenreViewModel) {
    val state = genreViewModel.screenState.collectAsStateWithLifecycle(
        initialValue = GenreScreenState.Initial
    )

    when (val currentState = state.value) {
        is GenreScreenState.Initial -> {
            GenreScreenContent(
                onSearchClick = {
                    genreViewModel.handleIntent(GenreScreenIntent.OnSearchClick(it))
                },
                onNewQuery = {
                /*    genreViewModel.handleIntent(GenreScreenIntent.OnQueryChange(it))*/
                },
                onClickBack = {
                    genreViewModel.handleEvent(GenreScreenIntent.Event.OnClickBack)
                },
                onChooseType = {
                    genreViewModel.handleEvent(GenreScreenIntent.Event.OnGenreClick(it))
                }
            )
        }

        is GenreScreenState.Loading -> {
            GenreLoadingScreen(
                onSearchClick = {
                    genreViewModel.handleIntent(GenreScreenIntent.OnSearchClick(it))
                },
                onNewQuery = {
                 /*   genreViewModel.handleIntent(GenreScreenIntent.OnQueryChange(it))*/
                }
            )
        }

        is GenreScreenState.Error -> {
            GenreErrorScreen(
                onSearchClick = {
                    genreViewModel.handleIntent(GenreScreenIntent.OnSearchClick(it))
                },
                onNewQuery = {
                  /*  genreViewModel.handleIntent(GenreScreenIntent.OnQueryChange(it))*/
                }
            )
        }

        is GenreScreenState.Success -> {
            GenreScreenContent(
                onSearchClick = {
                    genreViewModel.handleIntent(GenreScreenIntent.OnSearchClick(it))
                },
                onNewQuery = {
                    /*genreViewModel.handleIntent(GenreScreenIntent.OnQueryChange(it))*/
                },
                onClickBack = {
                    genreViewModel.handleEvent(GenreScreenIntent.Event.OnClickBack)
                },
                onChooseType = {
                    genreViewModel.handleEvent(GenreScreenIntent.Event.OnGenreClick(it))
                },
                genres = currentState.genres,
                query = currentState.query
            )
        }
    }

}

@Composable
private fun GenreScreenContent(
    query: String = "",
    genres: Genre = defaultGenre,
    onChooseType: (Genre) -> Unit,
    onSearchClick: (String) -> Unit,
    onNewQuery: (String) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    SortTypeSection(
        types = genres,
        topBarTitle = "Жанр",
        searchBarTitle = "Введите жанр",
        query = query,
        onChooseType = onChooseType,
        onClickBack = onClickBack,
        onNewQuery = onNewQuery,
        onSearchClick = onSearchClick,
        modifier = modifier
    )
}

@Composable
private fun GenreErrorScreen(
    onSearchClick: (String) -> Unit,
    onNewQuery: (String) -> Unit,
) {
    Column {
        CustomSearchBar(
            leadingIcon = R.drawable.icon_search,
            placeHolderText = "Введите жанр",
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
    Column {
        CustomSearchBar(
            leadingIcon = R.drawable.icon_search,
            placeHolderText = "Введите жанр",
            onSearchClick = onSearchClick,
            onNewQuery = onNewQuery,
            modifier = Modifier.padding(16.dp)
        )
        CircularProgressIndicator()
    }
}

















