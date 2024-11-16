package com.jax.movies.presentation.detail.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jax.movies.R
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.common.MyTopAppBar
import com.jax.movies.presentation.detail.movies.LoadingScreen
import com.jax.movies.presentation.home.ErrorScreen
import com.jax.movies.presentation.home.FetchedImage

@Composable
fun GalleryScreen(
    onClickBack: () -> Unit,
    movie: Movie
) {
    val viewModel: GalleryViewModel = viewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    when (val currentState = state.value) {
        is GalleryScreenState.Initial -> {}
        is GalleryScreenState.Loading -> LoadingScreen()
        is GalleryScreenState.Error -> ErrorScreen(currentState.message)
        is GalleryScreenState.Success -> GalleryImages(
            galleries = currentState.galleries,
            onClickBack = onClickBack
        )
    }
    LaunchedEffect(movie) {
        viewModel.fetchGalleries(movie.id)
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

/*@Composable
fun TestImages(
    modifier: Modifier = Modifier
) {
    val galleries = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
    LazyVerticalGrid(
        columns = GridCells.Adaptive(146.dp),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(galleries) { index, _ ->
            if (index % 3 != 0) {
                BoxTest(
                    modifier = Modifier
                        .width(146.dp)
                        .height(82.dp)
                )
            } else {
                BoxTest(
                    modifier = Modifier
                        .width(308.dp)
                        .height(173.dp)
                )
            }
        }
    }
}

@Composable
private fun BoxTest(
    modifier: Modifier
) {
    Box(modifier = modifier)
}*/










