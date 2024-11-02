package com.jax.movies.presentation.detail.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jax.movies.domain.entity.Movie


@Composable
fun MovieContent(
   movie: Movie
) {
    Column {
        Text(text = movie.name)
        Text(text = movie.year.toString())
        Text(text = movie.slogan)
        Text(text = movie.shortDescription)
        Text(text = movie.description)
    }
}