package com.jax.movies.presentation.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.R
import com.jax.movies.data.Movie
import com.jax.movies.data.UIState


@Composable
fun HomePage(
    uiState: UIState,
    onMovieClick: () -> Unit,
    onTypeClick: (String, List<Movie>) -> Unit
) {
    when (uiState) {
        is UIState.Initial -> {}
        is UIState.Loading -> {
            CircularProgressIndicator()
        }
        is UIState.Success -> {

            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp)
                    .verticalScroll(state = scrollState),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Spacer(Modifier.height(50.dp))
                Image(
                    painter = painterResource(R.drawable.vector),
                    contentDescription = null
                )
                val categories = listOf(
                    "Премьера", "Популярное",
                    "Боевики США", "Драма Франции", "Топ-250", "Сериалы"
                )

                for (i in 0..categories.size - 1) {
                    LazyRowItem(items = uiState.movies[i],
                        type = categories.get(i),
                        onMovieClick = onMovieClick)
                }
//        LazyRowItem(
//            items = AllMovies.premieres,
//            type = "Премьера",
//            onMovieClick = {
//                onMovieClick(it, "Премьера")
//            }
//        )
//        LazyRowItem(
//            items = AllMovies.popular,
//            type = "Популярное",
//            onMovieClick = {
//                onMovieClick(it, "Популярное")
//            }
//        )
//        LazyRowItem(
//            items = AllMovies.militants,
//            type = "Боевики США",
//            onMovieClick = {
//                onMovieClick(it, "Боевики США")
//            }
//        )
//        LazyRowItem(
//            items = AllMovies.dramaOfFrance,
//            type = "Драма Франции",
//            onMovieClick = {
//                onMovieClick(it, "Драма Франции")
//            }
//        )
//
//        LazyRowItem(
//            items = AllMovies.top,
//            type = "Топ-5",
//            onMovieClick = {
//                onMovieClick(it, "Топ-5")
//            }
//        )
//        LazyRowItem(
//            items = AllMovies.show,
//            type = "Сериалы",
//            onMovieClick = {
//                onMovieClick(it, "Сериалы")
//            }
//        )
            }
        }

        is UIState.Error -> {
            Text(
                text = " Error",
                color = Color.Red
            )
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    onMovieClick: () -> Unit
) {
    Column(modifier = Modifier.width(111.dp)
        .clickable { onMovieClick() }) {
        Box(modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .width(111.dp)
            .height(156.dp)){

            Image(painter = rememberAsyncImagePainter(model = movie.image),
                contentDescription = "poster of movie")

            Text(
                text = movie.name
            )
            Text(
                text = movie.genres.firstOrNull()?.genre.orEmpty()
            )
        }
    }

}

@Composable
fun LazyRowItem(
    items: List<Movie>,
    type: String,
    onMovieClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(111.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = type,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(items.take(6).size) { item ->
                MovieItem(
                    movie = items.get(item),
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}
