package com.jax.movies.presentation.detail.movie

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jax.movies.R
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.common.MyTopAppBar
import com.jax.movies.presentation.detail.movies.LoadingScreen
import com.jax.movies.presentation.home.main.ErrorScreen
import com.jax.movies.presentation.home.main.FetchedImage
import com.jax.movies.presentation.home.main.MovieItem

@Composable
fun MovieContent(
    movieDetailViewModel: MovieDetailViewModel,
    movie: Movie
) {
    Log.d("dasdddddddddd", movie.id.toString())
    val state by movieDetailViewModel.state.collectAsStateWithLifecycle()
    when (val currentState = state) {
        is MovieDetailState.Initial -> {}
        is MovieDetailState.Loading -> LoadingScreen()
        is MovieDetailState.Error -> ErrorScreen(errorMessage = currentState.message)
        is MovieDetailState.Success -> {
            MainContent(
                movie = currentState.movie,
                onGalleryClick = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnGalleryClick(movie))
                },
                onActorClick = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnActorClick(it))
                },
                onMovieClick = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnMovieClick(it))
                },
                onBackClicked = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnBackClicked(it))
                },
                onLikeClicked = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnLickClick)
                },
                onFavouriteClicked = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnFavouriteClick)
                },
                onShareClicked = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnShareClick)
                },
                onBlindEyeClicked = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnBlindEyeClick)
                },
                onMoreClicked = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnMoreClick)
                },
                galleries = currentState.gallery,
                actors = currentState.actors,
                filmCrew = currentState.filmCrew,
                similarMovies = currentState.similarMovies
            )
        }
    }
    LaunchedEffect(key1 = movie) {
        movieDetailViewModel.handleAction(MovieScreenAction.FetchMovieDetailInfo(movie))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun MainContent(
    onGalleryClick: (Movie) -> Unit,
    onActorClick: (Actor) -> Unit,
    onMovieClick: (Movie) -> Unit,
    onBackClicked: (Movie) -> Unit,
    onLikeClicked: () -> Unit,
    onFavouriteClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onBlindEyeClicked: () -> Unit,
    onMoreClicked: () -> Unit,
    movie: Movie,
    galleries: List<GalleryImage>,
    actors: List<Actor>,
    filmCrew: List<Actor>,
    similarMovies: List<Movie>,
    modifier: Modifier = Modifier
) {
    val actorsId = actors.map { it.actorId }
    Log.d("dasdasdsa", actorsId.toString())

    Scaffold(
        topBar = {
            MyTopAppBar(
                onNavClick = {onBackClicked(movie) },
                navIcon =R.drawable.icon_back,
                title = "")
        },
        modifier = Modifier.padding(bottom = 40.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(40.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(600.dp)
                ) {
                    FetchedImage(
                        linkToImage = movie.posterUrl,
                        modifierForParent = Modifier
                            .fillMaxWidth()
                            .matchParentSize(),
                    )
                    TitleSection(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        movie = movie,
                        onLikeClicked = onLikeClicked,
                        onFavouriteClicked = onFavouriteClicked,
                        onShareClicked = onShareClicked,
                        onBlindEyeClicked = onBlindEyeClicked,
                        onMoreClicked = onMoreClicked
                    )
                }
            }
            item {
                MainDescriptionWithSubDescription(
                    mainDescription = movie.description,
                    subDescription = movie.shortDescription,
                    modifier = Modifier
                        .padding(horizontal = 26.dp)
                )
            }
            item {
                ActorsSection(
                    title = "В фильме снимались",
                    countOther = actors.size,
                    itemInColumn = 2,
                    actors = actors,
                    onActorClick = onActorClick,
                    onTitleClick = {},
                    modifier = Modifier.padding(horizontal = 26.dp)
                )
            }
            item {
                ActorsSection(
                    title = "Над фильмом работали",
                    countOther = filmCrew.size,
                    itemInColumn = 2,
                    actors = filmCrew,
                    onActorClick = onActorClick,
                    onTitleClick = {},
                    modifier = Modifier.padding(horizontal = 26.dp)
                )
            }
            item {
                GallerySection(
                    count = galleries.size,
                    galleries = galleries,
                    onGalleryClick = { onGalleryClick(movie) },
                    modifier = Modifier.padding(horizontal = 26.dp)
                )
            }
            item {
                RelatedMoviesSection(
                    countOrAll = similarMovies.size.toString(),
                    relatedMovies = similarMovies,
                    onMovieClick = onMovieClick,
                    modifier = Modifier
                        .padding(horizontal = 26.dp)
                )
            }
        }
    }
}

@Composable
private fun TitleSection(
    movie: Movie,
    onLikeClicked: () -> Unit,
    onFavouriteClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onBlindEyeClicked: () -> Unit,
    onMoreClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "${movie.ratingKp} ${movie.name}",
            color = Color(0xFFB5B5C9),
            modifier = Modifier.padding(top = 40.dp)
        )
        Row {
            Text(text = "${movie.year}", color = Color(0xFFB5B5C9))
            movie.genres.forEachIndexed { index, genre ->
                if (index < movie.genres.size - 1) Text(text = ",")
                Text(text = genre.genre, color = Color(0xFFB5B5C9))
            }
        }
        Row {
            movie.countries.forEachIndexed { index, country ->
                if (index < movie.countries.size - 1) Text(text = ",")
                Text(text = country.country, color = Color(0xFFB5B5C9))
            }
            Text(text = "${movie.lengthMovie} ${movie.ageLimit}")
        }
        Row {
            IconButton(onClick = onLikeClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_liked),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(onClick = onFavouriteClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_favourite),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(onClick = onBlindEyeClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_blind_eye),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(onClick = onShareClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_share),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(onClick = onMoreClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_more),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun MainDescriptionWithSubDescription(
    mainDescription: String,
    subDescription: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = mainDescription,
            fontWeight = FontWeight.W700,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = subDescription,
            fontWeight = FontWeight.W400,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun StepTitle(
    onTitleClick: () -> Unit,
    title: String,
    subTitle: String? = null,
    countOrOther: String,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = countOrOther,
                fontWeight = FontWeight.W600,
                fontSize = 14.sp,
                color = Color(0xFF3D3BFF)

            )
            IconButton(onClick = onTitleClick) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = "see all",
                    tint = Color(0xFF3D3BFF)
                )
            }
        }
        if (subTitle?.isNotEmpty() == true) {
            Text(
                text = subTitle,
                fontWeight = FontWeight.W400,
                fontSize = 12.sp,
                color = Color(0xFFB5B5C9),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ActorsSection(
    onTitleClick: () -> Unit,
    onActorClick: (Actor) -> Unit,
    title: String,
    countOther: Int,
    itemInColumn: Int,
    actors: List<Actor>,
    modifier: Modifier = Modifier
) {
    Column {
        StepTitle(
            title = title,
            countOrOther = countOther.toString(),
            modifier = modifier,
            onTitleClick = onTitleClick
        )
        LazyHorizontalGrid(
            rows = GridCells.Fixed(itemInColumn),
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            items(actors) { actor ->
                ActorItem(
                    actor = actor,
                    onActorClick = onActorClick,
                    modifierForParent = Modifier.size(width = 207.dp, height = 68.dp)
                )
            }
        }
    }
}

@Composable
fun ActorItem(
    onActorClick: (Actor) -> Unit,
    actor: Actor,
    modifierForParent: Modifier = Modifier,
    modifierForImage: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifierForParent
    ) {
        FetchedImage(
            linkToImage = actor.posterUrl,
            modifierForParent = modifierForImage
                .width(49.dp)
                .height(68.dp)
                .clickable {
                    onActorClick(actor)
                    Log.d("dsaadas", actor.actorId.toString())
                }
        )
        Column {
            Text(
                text = actor.nameRu,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                color = Color(0xFF272727),
                overflow = TextOverflow.Ellipsis,
                lineHeight = 13.2.sp,
                modifier = Modifier.sizeIn(maxWidth = 126.dp)
            )
            Text(
                text = actor.nameEn,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = Color(0xFFB5B5C9),
                overflow = TextOverflow.Ellipsis,
                lineHeight = 13.2.sp,
                modifier = Modifier.sizeIn(maxWidth = 126.dp)
            )
        }
    }
}

@Composable
private fun GallerySection(
    onGalleryClick: () -> Unit,
    count: Int,
    galleries: List<GalleryImage>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        StepTitle(
            title = "Галерея",
            countOrOther = count.toString(),
            modifier = modifier,
            onTitleClick = onGalleryClick
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            galleries.forEach { galleryImage ->
                item {
                    GalleryCard(galleryImage = galleryImage)
                }
            }
        }
    }
}

@Composable
fun GalleryCard(
    galleryImage: GalleryImage,
    modifier: Modifier = Modifier
) {
    FetchedImage(
        linkToImage = galleryImage.imageUrl,
        modifierForParent = modifier
            .width(192.dp)
            .height(108.dp)
            .clip(RoundedCornerShape(8.dp)),
        modifierForImage = Modifier.size(width = 192.dp, height = 108.dp)
    )
}

@Composable
fun RelatedMoviesSection(
    title: String = "Похожие фильмы",
    onMovieClick: (Movie) -> Unit,
    countOrAll: String,
    relatedMovies: List<Movie>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        StepTitle(
            title = title,
            countOrOther = countOrAll,
            modifier = modifier,
            onTitleClick = {

            }
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            relatedMovies.forEach { similarMovie ->
                item {
                    MovieItem(
                        movie = similarMovie,
                        onMovieClick = onMovieClick,
                        modifier = Modifier.padding(bottom = 56.dp)
                    )
                }
            }
        }
    }
}

