package com.jax.movies.presentation.home.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jax.movies.R
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.entity.home.MoviesType
import com.jax.movies.presentation.components.ErrorScreen
import com.jax.movies.presentation.components.LoadingScreen
import com.jax.movies.presentation.components.MovieItem

@Composable
fun MoviesDetailScreen(
    moviesDetailViewModel: MoviesDetailViewModel,
    type: MoviesType
) {
    val state by moviesDetailViewModel.state.collectAsStateWithLifecycle()

    when (val currentState = state) {
        is MoviesScreenState.Initial -> {}
        is MoviesScreenState.Loading -> LoadingScreen()
        is MoviesScreenState.Error -> ErrorScreen(currentState.message)
        is MoviesScreenState.Success -> {
            MoviesList(
                movies = currentState.movies,
                moviesType = type.toString(),
                onMovieClick = {
                    moviesDetailViewModel.handleIntent(MoviesScreenIntent.OnMovieClick(it))
                },
                onClickBack = {
                    moviesDetailViewModel.handleIntent(MoviesScreenIntent.OnClickBack)
                }
            )
        }
    }
    LaunchedEffect(key1 = type) {
        moviesDetailViewModel.handleAction(MoviesScreenAction.FetchMoviesDetailInfo(type))
    }
}

@Composable
fun MoviesList(
    movies: List<Movie>,
    moviesType: String,
    onClickBack: () -> Unit,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchTopAppBar(onClickBack = { onClickBack() }, text = moviesType)
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = WindowInsets.navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding()
                )

        ) {
            items(movies) { movie ->
                MovieItem(
                    onMovieClick = { onMovieClick(movie) },
                    movie = movie
                )
            }
        }
    }
}

@Composable
private fun SearchTopAppBar(
    onClickBack: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(
                top = WindowInsets.statusBars
                    .asPaddingValues()
                    .calculateTopPadding()
            )
            .padding(16.dp)
    ) {
        IconButton(onClick = onClickBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.go_back),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = text,
            fontWeight = FontWeight.W900,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}


