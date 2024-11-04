package com.jax.movies.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jax.movies.data.Movie

@Composable
fun OneTypePage(category: String,
                movie: List<Movie>,
                onBackClick: ()-> Unit,
                onMovieClick: () -> Unit){
     Scaffold {
         paddingValues ->
         LazyVerticalGrid(modifier = Modifier
             .fillMaxSize()
             .padding(16.dp)
             .background(Color.White),
             columns = GridCells.Fixed(2),
             contentPadding = paddingValues,
             verticalArrangement = Arrangement.spacedBy(16.dp)) {

             items(movie.take(20).size){
                 index->
                 Box(modifier = Modifier.fillMaxWidth(),
                     contentAlignment = Alignment.Center){
                     MovieItem(movie[index], onMovieClick)
                 }
             }
         }
     }
}