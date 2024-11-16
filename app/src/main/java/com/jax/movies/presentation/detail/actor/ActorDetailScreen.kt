package com.jax.movies.presentation.detail.actor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
    onFilmographyClick: (Actor) -> Unit,
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
            onFilmographyClick = { onFilmographyClick(actor) },
            onMovieClick = onMovieClick,
            moviesWithActor = currentState.actor.movies,
            modifier = modifier
        )
    }
    /* LaunchedEffect(actor.actorId) {
         viewModel.fetchDetailInfo(actor)
     }*/
    val isLoaded = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!isLoaded.value) {
            viewModel.fetchDetailInfo(actor)
            isLoaded.value = true
        }
    }
}

@Composable
private fun MainContent(
    moviesWithActor: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    onFilmographyClick: () -> Unit,
    actor: Actor,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(
            start = 26.dp,
            top = 98.dp
        )
    ) {
        ActorItem(
            actor = actor,
            onActorClick = {},
            modifierForImage = Modifier
                .size(width = 146.dp, height = 201.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        ActorBestSection(
            onMovieClick = onMovieClick,
            moviesWithActor = moviesWithActor
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
        subTitle = "$countFilms фильмов",
        onTitleClick = onFilmographyClick
    )
}

















