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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.jax.movies.data.AllMovies


@Composable
fun HomePage(
    paddingValues: PaddingValues,
    onMovieClick: (id: Long, type: String) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
            .padding(start = 16.dp)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Spacer(Modifier.height(50.dp))
        Image(
            painter = painterResource(R.drawable.vector),
            contentDescription = null
        )
        LazyRowItem(
            items = AllMovies.premieres,
            type = "Премьера",
            onMovieClick = {
                onMovieClick(it, "Премьера")
            }
        )
        LazyRowItem(
            items = AllMovies.popular,
            type = "Популярное",
            onMovieClick = {
                onMovieClick(it, "Популярное")
            }
        )
        LazyRowItem(
            items = AllMovies.militants,
            type = "Боевики США",
            onMovieClick = {
                onMovieClick(it, "Боевики США")
            }
        )
        LazyRowItem(
            items = AllMovies.dramaOfFrance,
            type = "Драма Франции",
            onMovieClick = {
                onMovieClick(it, "Драма Франции")
            }
        )

        LazyRowItem(
            items = AllMovies.top,
            type = "Топ-5",
            onMovieClick = {
                onMovieClick(it, "Топ-5")
            }
        )
        LazyRowItem(
            items = AllMovies.show,
            type = "Сериалы",
            onMovieClick = {
                onMovieClick(it, "Сериалы")
            }
        )
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    onMovieClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    val animatedColor by animateColorAsState(
        targetValue = if (visible) Color.Yellow else Color.Cyan,
        animationSpec = tween(1000),
        label = "",
    )
    val colors = listOf(animatedColor, Color.Red, Color.Yellow)
    Column(
        modifier = Modifier.clickable { onMovieClick() }
    ) {
        val animateAlpha by animateFloatAsState(
            targetValue = if (visible) 1f else 0f,
            label = "",
            animationSpec = tween(durationMillis = 1000)
        )
        Image(
            painter = painterResource(R.drawable.zamena),
            contentDescription = "image of cinema",
            modifier = Modifier
                .height(156.dp)
                .width(111.dp)
                .alpha(animateAlpha)
                .background(brush = Brush.linearGradient(colors))
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = movie.title,
            fontSize = 14.sp
        )
        Text(
            text = movie.genre,
            fontSize = 12.sp
        )
    }
}

@Composable
fun LazyRowItem(
    items: List<Movie>,
    type: String,
    onMovieClick: (Long) -> Unit
) {

    var wrapped by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = type,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        val displayedItems = if (wrapped) items.take(items.size / 2) else items
        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(displayedItems) { item ->
                MovieItem(
                    movie = item,
                    onMovieClick = {
                        onMovieClick(item.id)
                    }
                )
            }
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
                    )
                }
            }
        }
    }
}
