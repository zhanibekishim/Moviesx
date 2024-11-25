package com.jax.movies.presentation.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.home.movie.StepTitle

@Composable
fun RelatedMoviesSection(
    title: String = "Похожие фильмы",
    onMovieClick: (Movie) -> Unit,
    countOrAll: String,
    relatedMovies: List<Movie>,
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
            }
        }
    }
}