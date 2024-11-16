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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jax.movies.R
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.ActorType
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.common.MyTopAppBar
import com.jax.movies.presentation.detail.movies.LoadingScreen
import com.jax.movies.presentation.home.ErrorScreen
import com.jax.movies.presentation.home.MovieItem

@Composable
fun FilmographyScreen(
    onClickBack: () -> Unit,
    onMovieClick: (Movie) -> Unit,
    actor: Actor,
    modifier: Modifier = Modifier,
) {
    val viewModel: FilmographyViewModel = viewModel(
        factory = FilmographyViewModel.FilmographyViewModelFactory(actor)
    )

    val state by viewModel.state.collectAsStateWithLifecycle()

    when (val currentState = state) {
        is FilmographyScreenState.Initial -> {}
        is FilmographyScreenState.Loading -> LoadingScreen()
        is FilmographyScreenState.Error -> ErrorScreen(currentState.message)
        is FilmographyScreenState.Success -> {
            FilmographyContent(
                onClickBack = onClickBack,
                onMovieClick = onMovieClick,
                onFilmographyClick = { selectedType ->
                    viewModel.getMoviesByFilmographyType(selectedType)
                },
                movies = currentState.movies,
                actorTypes = viewModel.actorTypes,
                modifier = modifier
            )
        }
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
                    onFilmographyClick = {actorType->
                        onFilmographyClick(actorType)
                    },
                    actorTypes = actorTypes
                )
            }
            items(movies) {movie->
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
    actorTypes:Map<ActorType, List<Movie>>,
    modifier: Modifier = Modifier,
) {
    var currentType by remember {
        mutableStateOf(actorTypes.keys.first())
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
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
                text = type.name,
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










