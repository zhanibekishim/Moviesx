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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.jax.movies.R
import com.jax.movies.domain.entity.Movie

@Composable
fun HomePage(
    onMoviesClick: (MoviesType) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val mainViewModel: MainViewModel = viewModel()
    val state = mainViewModel.moviesListState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ){
        Image(
            painter = painterResource(R.drawable.vector),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )
        Spacer(Modifier.height(24.dp))
        state.value.moviesList.forEach {moviesListState ->
            when (moviesListState) {
                is MoviesListState.MoviesState.Initial -> {}
                is MoviesListState.MoviesState.Loading -> LoadingScreen()
                is MoviesListState.MoviesState.Error -> ErrorScreen(errorMessage = moviesListState.message)
                is MoviesListState.MoviesState.Success -> {
                    MainContent(
                        movies = moviesListState.movies,
                        type = moviesListState.moviesType,
                        onMoviesClick = onMoviesClick,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun MainContent(
    movies: List<Movie>,
    type: MoviesType,
    onMoviesClick: (MoviesType) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRowItem(
        modifier= modifier,
        items = movies,
        type = type,
        onMoviesClick = onMoviesClick
    )
}

@Composable
private fun LazyRowItem(
    modifier: Modifier,
    items: List<Movie>,
    type: MoviesType,
    onMoviesClick: (MoviesType) -> Unit
) {
    var wrapped by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = type.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
        val displayedItems = if (wrapped) items.take(items.size / 2) else items
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable { onMoviesClick(type) }
        ) {
            items(displayedItems) { MovieItem(movie = it) }
            item {
                val icon = if (wrapped) R.drawable.arrow_right else R.drawable.arrowleft
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { wrapped = !wrapped }

                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "See more",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
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
            .width(111.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            model = movie.posterUrl,
            contentDescription = movie.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(156.dp)
                .width(111.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = movie.name,
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = movie.genres.first().toString(),
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
private fun LoadingScreen(
    modifier: Modifier = Modifier
) {
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
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = errorMessage, color = Color.Red)
    }
}










