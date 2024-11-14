package com.jax.movies.presentation.detail.movie

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.jax.movies.R
import com.jax.movies.domain.entity.Actor
import com.jax.movies.domain.entity.GalleryImage
import com.jax.movies.domain.entity.Movie
import com.jax.movies.presentation.home.MovieItem

@Composable
fun MovieContent(
    onMovieClick: (Movie) -> Unit,
    onBackClicked: () -> Unit,
    onLikeClicked: () -> Unit,
    onFavouriteClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onBlindEyeClicked: () -> Unit,
    onMoreClicked: () -> Unit,
    movie: Movie
) {
    Log.d("dasdddddddddd", movie.id.toString())
    val viewModel: MovieDetailViewModel = viewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    when (val currentState = state) {
        is MovieDetailState.Initial -> {}
        is MovieDetailState.Loading -> LoadingScreen()
        is MovieDetailState.Error -> ErrorScreen(errorMessage = currentState.message)
        is MovieDetailState.Success -> {
            MainContent(
                movie = currentState.movie,
                onMovieClick = onMovieClick,
                onBackClicked = onBackClicked,
                onLikeClicked = onLikeClicked,
                onFavouriteClicked = onFavouriteClicked,
                onShareClicked = onShareClicked,
                onBlindEyeClicked = onBlindEyeClicked,
                onMoreClicked = onMoreClicked,
                galleries = currentState.gallery,
                actors = currentState.actors,
                filmCrew = currentState.filmCrew,
                similarMovies = currentState.similarMovies
            )
        }
    }
    LaunchedEffect(key1 = movie) {
        viewModel.fetchDetailInfo(movie)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun MainContent(
    onMovieClick: (Movie) -> Unit,
    onBackClicked: () -> Unit,
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onBackClicked() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_back),
                            contentDescription = "back"
                        )
                    }
                }
            )
        }
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
                    GlideSubcomposition(
                        modifier = Modifier
                            .fillMaxWidth()
                            .matchParentSize(),
                        model = movie.posterUrl,
                        content = {
                            when (state) {
                                is RequestState.Failure -> ErrorItem(modifier = Modifier.fillMaxSize())
                                is RequestState.Loading -> LoadingItem(modifier = Modifier.fillMaxSize())
                                is RequestState.Success -> Image(
                                    painter = painter,
                                    contentDescription = movie.name,
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
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
                    modifier = Modifier.padding(horizontal = 26.dp)
                )
            }
            item {
                ActorsSection(
                    title = "Над фильмом работали",
                    countOther = filmCrew.size,
                    itemInColumn = 2,
                    actors = filmCrew,
                    modifier = Modifier.padding(horizontal = 26.dp)
                )
            }
            item {
                GallerySection(
                    count = galleries.size,
                    galleries = galleries,
                    modifier = Modifier.padding(horizontal = 26.dp)
                )
            }
            item {
                SimilarMoviesSection(
                    count = similarMovies.size,
                    similarMovies = similarMovies,
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
        Row{
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
private fun StepTitle(
    title: String,
    count: Int,
    modifier: Modifier = Modifier
) {
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
            text = count.toString(),
            fontWeight = FontWeight.W600,
            fontSize = 14.sp,
            color = Color(0xFF3D3BFF)

        )
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "see all",
                tint = Color(0xFF3D3BFF)
            )
        }
    }
}

@Composable
private fun ActorsSection(
    title: String,
    countOther: Int,
    itemInColumn: Int,
    actors: List<Actor>,
    modifier: Modifier = Modifier
) {
    Column {
        StepTitle(title = title, count = countOther, modifier = modifier)
        LazyHorizontalGrid(
            rows = GridCells.Fixed(itemInColumn),
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            items(actors) { actor ->
                ActorItem(actor = actor)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ActorItem(
    actor: Actor,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.size(width = 207.dp, height = 68.dp)
    ) {
        GlideSubcomposition(
            model = actor.posterUrl,
            modifier = Modifier
                .width(49.dp)
                .height(68.dp),
            content = {
                when (state) {
                    is RequestState.Failure -> ErrorItem(modifier = Modifier.fillMaxSize())
                    is RequestState.Loading -> LoadingItem(modifier = Modifier.fillMaxSize())
                    is RequestState.Success -> Image(
                        painter = painter,
                        contentDescription = actor.nameEn,
                        contentScale = ContentScale.Crop
                    )
                }
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
    count: Int,
    galleries: List<GalleryImage>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        StepTitle(title = "Галерея", count = count, modifier = modifier)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            galleries.forEach { galleryImage ->
                item { GalleryCard(galleryImage = galleryImage) }
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GalleryCard(
    galleryImage: GalleryImage,
    modifier: Modifier = Modifier
) {
    GlideSubcomposition(
        model = galleryImage.imageUrl,
        modifier = modifier
            .width(192.dp)
            .height(108.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {},
        content = {
            when (state) {
                is RequestState.Failure -> ErrorItem(modifier = Modifier.fillMaxSize())
                is RequestState.Loading -> LoadingItem(modifier = Modifier.fillMaxSize())
                is RequestState.Success -> Image(
                    painter = painter,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(width = 192.dp, height = 108.dp)
                )
            }
        }
    )
}

@Composable
private fun SimilarMoviesSection(
    onMovieClick: (Movie) -> Unit,
    count: Int,
    similarMovies: List<Movie>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier){
        StepTitle(title = "Похожие фильмы", count = count, modifier = modifier)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            similarMovies.forEach { similarMovie ->
                item { MovieItem(
                    movie = similarMovie,
                    onMovieClick = onMovieClick,
                    modifier = Modifier.padding(bottom = 56.dp)
                ) }
            }
        }
    }
}

@Composable
private fun ErrorItem(modifier: Modifier) {
    Text(text = "Ошибка", modifier = modifier)
}

@Composable
private fun LoadingItem(modifier: Modifier) {
    CircularProgressIndicator(modifier = modifier)
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