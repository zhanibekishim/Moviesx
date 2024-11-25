package com.jax.movies.presentation.home.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jax.movies.R
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.components.ErrorScreen
import com.jax.movies.presentation.components.FetchedImage
import com.jax.movies.presentation.components.LoadingScreen
import com.jax.movies.presentation.components.MyTopAppBar

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
private fun GalleryImages(
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
        LazyColumn(
            modifier = modifier.padding(padding),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(galleries.chunked(2)) { index, pair ->
                if (index % 2 == 0) {
                    FetchedImage(
                        linkToImage = pair[0].imageUrl,
                        modifierForParent = Modifier
                            .fillMaxWidth()
                            .height(92.dp)
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FetchedImage(
                            linkToImage = pair[0].imageUrl,
                            modifierForParent = Modifier
                                .weight(1f)
                                .height(92.dp),
                            modifierForImage = Modifier.clip(RoundedCornerShape(8.dp))
                        )
                        FetchedImage(
                            linkToImage = pair[1].imageUrl,
                            modifierForParent = Modifier
                                .weight(1f)
                                .height(92.dp),
                            modifierForImage = Modifier.clip(RoundedCornerShape(8.dp))
                        )
                    }
                }
            }
        }

    }
}














