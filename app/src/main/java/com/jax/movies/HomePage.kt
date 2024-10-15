package com.jax.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.data.Movie
import com.jax.movies.data.allMovies

@Composable
fun HomePage(){
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize()
        .padding(18.dp)
        .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Spacer(Modifier.height(50.dp))
        Image(painter = painterResource(R.drawable.vector),
            contentDescription = null)
        LazyRowItem(allMovies.premieres, "Премьера")
        LazyRowItem(allMovies.popular, "Популярное")
        LazyRowItem(allMovies.militants, "Боевики США")
        LazyRowItem(allMovies.dramaOfFrance, "Драма Франции")
        LazyRowItem(allMovies.top, "Топ-5")
        LazyRowItem(allMovies.show, "Сериалы")
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Column {
        Image(painter = painterResource(R.drawable.zamena),
            contentDescription = "image of cinema",
            modifier = Modifier.height(156.dp)
                .width(111.dp))
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
fun LazyRowItem(items: List<Movie>,
                type: String){
    Column(modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(text = type,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold)
        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                MovieItem(item)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome(){
    HomePage()
}