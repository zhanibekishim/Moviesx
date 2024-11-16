package com.jax.movies.presentation.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.jax.movies.R
import com.jax.movies.domain.entity.home.Movie
import com.valentinilk.shimmer.shimmer

@Composable
fun HomePage(
    onListClick: (MoviesType) -> Unit,
    onMovieClick: (Movie) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val homeViewModel: HomeViewModel = viewModel()
    val state = homeViewModel.homeScreenState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(R.drawable.vector),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )
        Spacer(Modifier.height(24.dp))

        HandleMovieList(
            title = "Top 250 Movies",
            moviesState = state.value.top250MoviesState,
            moviesType = MoviesType.TOP_250_MOVIES,
            onListClick = onListClick,
            onMovieClick = onMovieClick
        )

        HandleMovieList(
            title = "Popular Movies",
            moviesState = state.value.popularMoviesState,
            moviesType = MoviesType.TOP_POPULAR_MOVIES,
            onListClick = onListClick,
            onMovieClick = onMovieClick
        )

        HandleMovieList(
            title = "Comics Theme",
            moviesState = state.value.comicsMoviesState,
            moviesType = MoviesType.COMICS_THEME,
            onListClick = onListClick,
            onMovieClick = onMovieClick
        )

        HandleMovieList(
            title = "Premiers",
            moviesState = state.value.premiersMoviesState,
            moviesType = MoviesType.PREMIERS,
            onListClick = onListClick,
            onMovieClick = onMovieClick
        )
    }
}

@Composable
private fun HandleMovieList(
    title: String,
    moviesState: HomeScreenState.MoviesState,
    moviesType: MoviesType,
    onListClick: (MoviesType) -> Unit,
    onMovieClick: (Movie) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        when (moviesState) {
            is HomeScreenState.MoviesState.Loading -> {
                ShimmerLoadingItems()
            }

            is HomeScreenState.MoviesState.Success -> {
                LazyRowItem(
                    items = moviesState.movies,
                    onListClick = onListClick,
                    moviesType = moviesType,
                    title = title,
                    onMovieClick = onMovieClick
                )
            }

            is HomeScreenState.MoviesState.Error -> ErrorScreen(errorMessage = moviesState.message)
            HomeScreenState.MoviesState.Initial -> Unit
        }
    }
}

@Composable
private fun LazyRowItem(
    items: List<Movie>,
    title: String,
    moviesType: MoviesType,
    onMovieClick: (Movie) -> Unit,
    onListClick: (MoviesType) -> Unit
) {
    var wrapped by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            item {
                Text(
                    text = stringResource(R.string.seeAll),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    modifier = Modifier
                        .clickable { onListClick(moviesType) }
                )
            }
        }

        val displayedItems = if (wrapped) items.take(items.size / 2) else items
        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(displayedItems) { movie ->
                MovieItem(
                    movie = movie,
                    onMovieClick = { onMovieClick(movie) }
                )
            }
            item {
                val icon = if (wrapped) R.drawable.arrow_right else R.drawable.arrowleft
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { wrapped = !wrapped }
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "See more",
                        tint = Color.Blue,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    vertically: Boolean = true,
    ratingPosition: Alignment = Alignment.TopEnd,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {

    val layout: @Composable (modifier: Modifier, content: @Composable () -> Unit) -> Unit =
        if (vertically) {
            { mod, content ->
                Column(
                    modifier = mod,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = { content() })
            }
        } else {
            { mod, content ->
                Row(
                    modifier = mod,
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    content = { content() }
                )
            }
        }

    layout(
        modifier = modifier
            .padding(8.dp)
            .then(
                if (vertically) Modifier
                    .wrapContentHeight()
                    .width(150.dp) else Modifier.fillMaxWidth()
            )
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clickable { onMovieClick(movie) },
            contentAlignment = ratingPosition
        ) {
            FetchedImage(
                linkToImage = movie.posterUrl,
                modifierForParent = Modifier
                    .sizeIn(maxHeight = 150.dp, maxWidth = 150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                modifierForImage = Modifier
            )
            if (movie.ratingKp != 0.0) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp)
                        .background(Color.Blue.copy(alpha = 0.6f), AbsoluteRoundedCornerShape(4.dp))
                ) {
                    Text(
                        text = movie.ratingKp.toString(),
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Column{
            Text(
                text = movie.name.takeIf { it.isNotEmpty() } ?: "No Name",
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = movie.genres.firstOrNull()?.genre.toString(),
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FetchedImage(
    linkToImage: String,
    modifierForParent: Modifier = Modifier,
    modifierForImage: Modifier = Modifier
) {
    GlideSubcomposition(
        modifier = modifierForParent,
        model = linkToImage,
        content = {
            when (state) {
                is RequestState.Failure -> ErrorItem()
                is RequestState.Loading -> LoadingItem()
                is RequestState.Success -> Image(
                    painter = painter,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = modifierForImage
                )
            }
        }
    )
}

@Composable
fun LoadingItem(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.sizeIn(maxHeight = 100.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorItem(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Something went wrong")
    }
}

@Composable
private fun ShimmerLoadingItems(
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        items(10) {
            Box(
                modifier = Modifier
                    .size(111.dp, 156.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
                    .shimmer()
            )
        }
    }
}

@Composable
fun ErrorScreen(
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
