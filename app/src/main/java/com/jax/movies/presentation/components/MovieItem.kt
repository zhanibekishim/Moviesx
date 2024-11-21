package com.jax.movies.presentation.components
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.domain.entity.home.Movie

@Composable
fun MovieItem(
    movie: Movie,
    vertically: Boolean = true,
    ratingPosition: Alignment = Alignment.TopEnd,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {

    val layout: @Composable (modifier: Modifier, content: @Composable () -> Unit) -> Unit =
        if (vertically) {
            { mod, content ->
                Column(
                    modifier = mod,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = { content() })
            }
        } else {
            { mod, content ->
                Row(
                    modifier = mod,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    content = { content() }
                )
            }
        }

    layout(
        modifier = modifier
            .padding(8.dp)
            .then(
                if (vertically) Modifier
                    .wrapContentHeight()
                    .width(150.dp) else Modifier.fillMaxWidth()
            )
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clickable { onMovieClick(movie) },
            contentAlignment = ratingPosition
        ) {
            FetchedImage(
                linkToImage = movie.posterUrl,
                modifierForParent = Modifier
                    .sizeIn(maxHeight = 150.dp, maxWidth = 150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                modifierForImage = Modifier
            )
            if (movie.ratingKp != 0.0) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp)
                        .background(Color.Blue.copy(alpha = 0.6f), AbsoluteRoundedCornerShape(4.dp))
                ) {
                    Text(
                        text = movie.ratingKp.toString(),
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Column {
            Text(
                text = movie.name.takeIf { it.isNotEmpty() } ?: "No Name",
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = movie.genres.firstOrNull()?.genre.toString(),
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.width(100.dp)
            )
        }
    }
}
