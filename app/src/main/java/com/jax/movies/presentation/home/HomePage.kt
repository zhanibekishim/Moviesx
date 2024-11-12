package com.jax.movies.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.jax.movies.R
import com.jax.movies.domain.entity.Movie
import com.valentinilk.shimmer.shimmer

@Composable
fun HomePage(
    onMoviesClick: (MoviesType) -> Unit,
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
            onMoviesClick = onMoviesClick
        )


        HandleMovieList(
            title = "Popular Movies",
            moviesState = state.value.popularMoviesState,
            moviesType = MoviesType.TOP_POPULAR_MOVIES,
            onMoviesClick = onMoviesClick
        )

        HandleMovieList(
            title = "Comics Theme",
            moviesState = state.value.comicsMoviesState,
            moviesType = MoviesType.COMICS_THEME,
            onMoviesClick = onMoviesClick
        )

        HandleMovieList(
            title = "Premiers",
            moviesState = state.value.premiersMoviesState,
            moviesType = MoviesType.PREMIERS,
            onMoviesClick = onMoviesClick
        )
    }
}

@Composable
private fun HandleMovieList(
    title: String,
    moviesState: HomeScreenState.MoviesState,
    moviesType: MoviesType,
    onMoviesClick: (MoviesType) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )

        when (moviesState) {
            is HomeScreenState.MoviesState.Loading -> {
                ShimmerLoadingItems()
            }

            is HomeScreenState.MoviesState.Success -> {
                LazyRowItem(
                    items = moviesState.movies,
                    onMoviesClick = onMoviesClick,
                    moviesType = moviesType
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
    moviesType: MoviesType,
    onMoviesClick: (MoviesType) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(items) { movie ->
            MovieItem(
                movie = movie,
                modifier = Modifier.clickable { onMoviesClick(moviesType) })
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .width(111.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideSubcomposition(
            modifier = Modifier.sizeIn(maxHeight = 100.dp),
            model = movie.posterUrl,
            content = {
                val imageModifier = Modifier
                    .sizeIn(maxHeight = 100.dp)
                    .clip(RoundedCornerShape(8.dp))

                when (state) {
                    is RequestState.Failure -> {

                    }

                    is RequestState.Loading -> {
                        LoadingItem(modifier = imageModifier)
                    }

                    is RequestState.Success -> {
                        Image(
                            painter = painter,
                            contentDescription = movie.name,
                            contentScale = ContentScale.Crop,
                            modifier = imageModifier
                        )
                    }
                }
            }
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = movie.name.takeIf { it.isNotEmpty() } ?: "No Name",
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = movie.genres.firstOrNull()?.toString() ?: "No Genre",
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}


@Composable
private fun LoadingItem(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
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
private fun ErrorScreen(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = errorMessage, color = Color.Red)
    }
}

@Preview(showBackground = true)
@Composable
private fun Test(){
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(Color.Red)
            .clip(RoundedCornerShape(topStart = 4.dp))
            .background(Color.Blue)
            .clip(RoundedCornerShape(bottomEnd = 4.dp))

    )
}
