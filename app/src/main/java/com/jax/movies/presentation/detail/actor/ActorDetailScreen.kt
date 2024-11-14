package com.jax.movies.presentation.detail.actor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.detail.movie.ActorItem
import com.jax.movies.presentation.detail.movie.RelatedMoviesSection
import com.jax.movies.presentation.detail.movie.StepTitle
import com.jax.movies.presentation.detail.movies.LoadingScreen
import com.jax.movies.presentation.home.ErrorScreen

@Composable
fun ActorDetailScreen(
    onMovieClick: (Movie) -> Unit,
    onFilmographyClick: () -> Unit,
    actor: Actor,
    modifier: Modifier = Modifier
) {
    val viewModel: ActorDetailViewModel = viewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    when (val currentState = state.value) {
        is ActorDetailState.Initial -> {}
        is ActorDetailState.Loading -> LoadingScreen()
        is ActorDetailState.Error -> ErrorScreen(currentState.message)
        is ActorDetailState.Success -> MainContent(
            actor = currentState.actor,
            onFilmographyClick = onFilmographyClick,
            onMovieClick = onMovieClick,
            modifier = modifier
        )
    }
    LaunchedEffect(actor) {
        viewModel.fetchDetailInfo(actor)
    }
}

@Composable
private fun MainContent(
    onMovieClick: (Movie) -> Unit,
    onFilmographyClick: () -> Unit,
    actor: Actor,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ActorItem(
            actor = actor,
            onActorClick = {},
            modifier = Modifier
                .size(width = 146.dp, height = 201.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        ActorBestSection(
            onMovieClick = onMovieClick,
            moviesWithActor = emptyList()
        )
        ActorFilmographySection(
            countFilms = actor.movies.size,
            onFilmographyClick = onFilmographyClick
        )
    }
}

@Composable
private fun ActorBestSection(
    onMovieClick: (Movie) -> Unit,
    moviesWithActor: List<Movie>,
    modifier: Modifier = Modifier
) {
    RelatedMoviesSection(
        onMovieClick = onMovieClick,
        countOrAll = "all",
        relatedMovies = moviesWithActor,
        modifier = modifier
    )
}

@Composable
private fun ActorFilmographySection(
    onFilmographyClick: () -> Unit,
    countFilms: Int
) {
    StepTitle(
        title = "Фильмография",
        countOrOther = countFilms.toString(),
        onTitleClick = onFilmographyClick
    )
}

















