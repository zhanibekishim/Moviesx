package com.jax.movies.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jax.movies.R
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.home.movie.StepTitle

@Composable
fun RelatedMoviesSection(
    title: String = "Похожие фильмы",
    onMovieClick: (Movie) -> Unit,
    countOrAll: String,
    relatedMovies: List<Movie>,
    deleteMoviesIcon: Boolean = false,
    deleteMoviesIconClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        StepTitle(
            title = title,
            countOrOther = countOrAll,
            modifier = modifier,
            onTitleClick = {

            }
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            relatedMovies.forEach { similarMovie ->
                item {
                    MovieItem(
                        movie = similarMovie,
                        onMovieClick = onMovieClick,
                        modifier = Modifier.padding(bottom = 56.dp)
                    )
                }
                if (deleteMoviesIcon) {
                    item {
                       Box(
                           contentAlignment = Alignment.Center,
                           modifier = Modifier.background(Color.White).padding(8.dp)
                       ){
                           IconButton(onClick = deleteMoviesIconClicked) {
                               Icon(
                                   painter = painterResource(id = R.drawable.icon_basket),
                                   contentDescription = "delete",
                                   modifier = Modifier.size(20.dp)
                               )
                           }
                       }
                    }
                }
            }
        }
    }
}