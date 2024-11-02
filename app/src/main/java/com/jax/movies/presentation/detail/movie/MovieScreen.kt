package com.jax.movies.presentation.detail.movie

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jax.movies.domain.entity.Movie


@Composable
fun MovieContent(
    movie: Movie
) {
    val viewModel: MovieDetailViewModel = viewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    when (val currentState = state) {
        is MovieDetailState.Error -> ErrorScreen(errorMessage = currentState.message)
        MovieDetailState.Initial -> {}
        MovieDetailState.Loading -> LoadingScreen()
        is MovieDetailState.Success -> {
            MainContent(movie = currentState.movie)}
    }
    LaunchedEffect(key1 = movie) {
        viewModel.fetchDetailInfo(movie)
    }
}

@Composable
private fun MainContent(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(text = movie.name)
        Text(text = movie.description)
        Text(text = movie.shortDescription)
        Text(text = movie.year.toString())
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
