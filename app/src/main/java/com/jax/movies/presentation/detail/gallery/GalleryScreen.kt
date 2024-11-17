package com.jax.movies.presentation.detail.gallery

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jax.movies.R
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.common.MyTopAppBar
import com.jax.movies.presentation.detail.movies.LoadingScreen
import com.jax.movies.presentation.home.main.ErrorScreen
import com.jax.movies.presentation.home.main.FetchedImage

@Composable
fun GalleryScreen(
    galleryViewModel: GalleryViewModel,
    movie: Movie
) {
    val state = galleryViewModel.state.collectAsStateWithLifecycle()
    when (val currentState = state.value) {
        is GalleryScreenState.Initial -> {}
        is GalleryScreenState.Loading -> LoadingScreen()
        is GalleryScreenState.Error -> ErrorScreen(currentState.message)
        is GalleryScreenState.Success -> GalleryImages(
            galleries = currentState.galleries,
            onClickBack = {
                galleryViewModel.handleIntent(GalleryScreenIntent.OnClickBack)
            }
        )
    }
    LaunchedEffect(movie) {
        galleryViewModel.handleAction(GalleryScreenAction.FetchGalleries(movie))
    }
}

@Composable
fun GalleryImages(
    onClickBack: () -> Unit,
    galleries: List<GalleryImage>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                onNavClick = onClickBack,
                navIcon = R.drawable.icon_back,
                title = "Галерея"
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(146.dp),
            modifier = modifier.padding(padding),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(galleries) {
                FetchedImage(linkToImage = it.imageUrl)
            }
        }
    }
}

@Preview
@Composable
fun RepeatingGrid() {
    val items = listOf(
        "Doug", "Ernie", "Fred", "George",
        "Doug", "Ernie", "Fred", "George",
        "Doug", "Ernie", "Fred", "George"
    )
    val chunked = remember {
        mutableIntStateOf(2)
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(items.chunked(2)) {index, group ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .border(1.dp, Color.Black)
                ) {
                    Text(
                        text = group[0],
                        modifier = Modifier.align(Alignment.Center)
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    group.drop(1).forEach { item ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .border(1.dp, Color.Black)
                        ) {
                            Text(
                                text = item,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}














