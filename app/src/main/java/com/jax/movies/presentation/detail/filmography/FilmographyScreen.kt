package com.jax.movies.presentation.detail.filmography

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jax.movies.R
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.ActorType
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.common.MyTopAppBar
import com.jax.movies.presentation.detail.movies.LoadingScreen
import com.jax.movies.presentation.home.main.ErrorScreen
import com.jax.movies.presentation.home.main.MovieItem

@Composable
fun FilmographyScreen(
    filmographyViewModel: FilmographyViewModel,
    actor: Actor,
    modifier: Modifier = Modifier,
) {

    val state by filmographyViewModel.state.collectAsStateWithLifecycle()

    when (val currentState = state) {
        is FilmographyScreenState.Initial -> {}
        is FilmographyScreenState.Loading -> LoadingScreen()
        is FilmographyScreenState.Error -> ErrorScreen(currentState.message)
        is FilmographyScreenState.Success -> {
            FilmographyContent(
                onClickBack = {
                    filmographyViewModel.handleIntent(FilmographyScreenIntent.OnClickBack)
                },
                onMovieClick = {
                    filmographyViewModel.handleIntent(FilmographyScreenIntent.OnMovieClick(it))
                },
                onFilmographyClick = {
                    filmographyViewModel.handleAction(FilmographyScreenAction.FetchMovies(it))
                },
                movies = currentState.movies,
                actorTypes = filmographyViewModel.actorTypes,
                modifier = modifier
            )
        }
    }

    LaunchedEffect(Unit) {
        filmographyViewModel.handleAction(FilmographyScreenAction.FetchFilmography(actor))
    }
}

@Composable
private fun FilmographyContent(
    onClickBack: () -> Unit,
    onFilmographyClick: (ActorType) -> Unit,
    onMovieClick: (Movie) -> Unit,
    actorTypes: Map<ActorType, List<Movie>>,
    movies: List<Movie>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                onNavClick = onClickBack,
                navIcon = R.drawable.icon_back,
                title = "Фильмография"
            )
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(it)
        ) {
            item {
                FilmographyTitle(
                    onFilmographyClick = { actorType ->
                        onFilmographyClick(actorType)
                    },
                    actorTypes = actorTypes
                )
            }
            items(movies) { movie ->
                MovieItem(
                    movie = movie,
                    onMovieClick = onMovieClick,
                    ratingPosition = Alignment.TopStart,
                    vertically = false,
                    modifier = Modifier.padding(26.dp)
                )
            }
        }
    }
}

@Composable
private fun FilmographyTitle(
    onFilmographyClick: (ActorType) -> Unit,
    actorTypes: Map<ActorType, List<Movie>>,
    modifier: Modifier = Modifier,
) {
    var currentType by remember {
        mutableStateOf(actorTypes.keys.first())
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(start = 26.dp)
    ) {
        actorTypes.forEach { (type, count) ->
            TypeWithCount(
                type = type,
                count = count.size,
                isSelected = currentType == type,
                isSelectedTextColor = Color.White,
                unSelectedTextColor = Color.Black,
                isSelectedContainerColor = Color.Blue,
                unSelectedContainerColor = Color.LightGray,
                onFilmographyClick = {
                    onFilmographyClick(it)
                    currentType = it
                }
            )

        }
    }
}

@Composable
private fun TypeWithCount(
    count: Int,
    type: ActorType,
    isSelected: Boolean,
    isSelectedTextColor: Color = Color.White,
    unSelectedTextColor: Color = Color.Gray,
    isSelectedContainerColor: Color = Color(0xFF3D3BFF),
    unSelectedContainerColor: Color = Color.White,
    onFilmographyClick: (ActorType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isSelected) isSelectedContainerColor else unSelectedContainerColor
    val textColor = if (isSelected) isSelectedTextColor else unSelectedTextColor
    Box(
        modifier = modifier
            .size(width = 144.dp, height = 36.dp)
            .clip(RoundedCornerShape(56.dp))
            .background(backgroundColor)
            .clickable { onFilmographyClick(type) }
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = type.toPlayRole(),
                color = textColor,
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = count.toString(),
                color = textColor,
                fontSize = 10.sp
            )
        }
    }
}

private fun ActorType.toPlayRole(): String {
    return when(this){
        ActorType.WRITE -> "Играет писателя"
        ActorType.OPERATOR -> "Играет оператора"
        ActorType.EDITOR -> "Играет эдитора"
        ActorType.COMPOSER -> "Играет компоузера"
        ActorType.PRODUCER_USSR -> "Играет  юсср"
        ActorType.TRANSLATOR -> "Играет переводчика"
        ActorType.DIRECTOR -> { "Играет директора"}
        ActorType.DESIGN -> { "Играет дизайнера"}
        ActorType.PRODUCER -> { "Играет продюсера"}
        ActorType.ACTOR -> { "Играет саму себя"}
        ActorType.VOICE_DIRECTOR -> { "Играет войс продюсера"}
        ActorType.UNKNOWN -> { "Неизвестный роль"}
    }
}









