package com.jax.movies.presentation.detail.movies

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.jax.movies.R
import com.jax.movies.domain.entity.Movie
import com.jax.movies.presentation.home.MoviesType

@Composable
fun MoviesDetailScreen(
    type: MoviesType,
    onMovieClick: (Movie) -> Unit,
    onClickBack: () -> Unit,
) {

    val viewModel: MoviesDetailViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    when (val currentState = state) {
        is MoviesDetailState.Initial -> {}
        is MoviesDetailState.Loading -> LoadingScreen()
        is MoviesDetailState.Error -> ErrorScreen(currentState.message)
        is MoviesDetailState.Success -> {
            MainContent(
                movies = currentState.movies,
                moviesType = type,
                onMovieClick = onMovieClick,
                onClickBack = onClickBack
            )
        }
    }
    LaunchedEffect(key1 = type) {
        viewModel.fetchMoviesDetail(type)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MainContent(
    movies: List<Movie>,
    moviesType: MoviesType,
    onClickBack: () -> Unit,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d("dsadasdasasdas", "MainContent Screen$movies")
    Column(modifier = modifier.fillMaxSize()) {
        SearchTopAppBar(onClickBack = { onClickBack() }, text = moviesType.name)
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
                Column(
                    modifier = Modifier
                        .clickable { onMovieClick(movie) }
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GlideImage(
                        model = movie.posterUrl,
                        contentDescription = movie.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(156.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Text(text = movie.name, textAlign = TextAlign.Center)
                    Text(
                        text = if (movie.genres.isNotEmpty() && movie.genres[0].genre.isNotEmpty()) {
                            movie.genres[0].genre
                        } else {
                            "No genre available"
                        },
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


@Composable
private fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Log.d("dsadasdasasdas", "Loading Screen")
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorScreen(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Log.d("dsadasdasasdas", "ErrorScreen: $errorMessage")
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = errorMessage, color = Color.Red)
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
            ).padding(16.dp)
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

