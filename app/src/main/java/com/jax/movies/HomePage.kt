package com.jax.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.data.Movie

@Composable
fun HomePage(){
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "SkillCinema",
            fontSize = 26.sp)
        val premieres = listOf(
            Movie(title = "Premiere 1", genre = "Genre 1"),
            Movie(title = "Premiere 2", genre = "Genre 2"),
            Movie(title = "Premiere 3", genre = "Genre 3")
        )
        LazyRow(modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(premieres){
                item -> MovieItem(item)
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Column {
        Box(
            modifier = Modifier.height(156.dp)
                .width(111.dp).background(color = Color.Gray)
        )
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

@Preview(showBackground = true)
@Composable
fun PreviewHome(){
    HomePage()
}