package com.jax.movies.presentation.detail.actor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jax.movies.R
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.common.MyTopAppBar
import com.jax.movies.presentation.detail.movie.ActorItem
import com.jax.movies.presentation.detail.movie.RelatedMoviesSection
import com.jax.movies.presentation.detail.movie.StepTitle
import com.jax.movies.presentation.detail.movies.LoadingScreen
import com.jax.movies.presentation.home.main.ErrorScreen

@Composable
fun ActorDetailScreen(
    actorDetailViewModel: ActorDetailViewModel,
    actor: Actor,
    modifier: Modifier = Modifier
) {
    val state = actorDetailViewModel.state.collectAsStateWithLifecycle()
    when (val currentState = state.value) {
        is ActorDetailState.Initial -> {}
        is ActorDetailState.Loading -> LoadingScreen()
        is ActorDetailState.Error -> ErrorScreen(currentState.message)
        is ActorDetailState.Success -> MainContent(
            actor = currentState.actor,
            onFilmographyClick = {
                actorDetailViewModel.handleIntent(ActorScreenIntent.OnFilmographyClick(it))
            },
            onMovieClick = {
                actorDetailViewModel.handleIntent(ActorScreenIntent.OnMovieClick(it))
            },
            onClickBack = {
                actorDetailViewModel.handleIntent(ActorScreenIntent.OnClickBack)
            },
            moviesWithActor = currentState.actor.movies,
            modifier = modifier
        )
    }
    LaunchedEffect(actor.actorId) {
        actorDetailViewModel.handleAction(ActorScreenAction.FetchActorDetailInfo(actor))
    }
}

@Composable
private fun MainContent(
    onClickBack: () -> Unit,
    moviesWithActor: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    onFilmographyClick: (Actor) -> Unit,
    actor: Actor,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                onNavClick = onClickBack,
                navIcon = R.drawable.icon_back,
                title = ""
            )
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(start = 26.dp,)
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
                onFilmographyClick = { onFilmographyClick(actor) }
            )
        }
    }
}

@Composable
private fun ActorBestSection(
    onMovieClick: (Movie) -> Unit,
    moviesWithActor: List<Movie>,
    modifier: Modifier = Modifier
) {
    RelatedMoviesSection(
        title = "Лучшее",
        onMovieClick = onMovieClick,
        countOrAll = "все",
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

















