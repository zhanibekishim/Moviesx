package com.jax.movies.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FetchedImage(
    linkToImage: String,
    modifierForParent: Modifier = Modifier,
    modifierForImage: Modifier = Modifier
) {
    GlideSubcomposition(
        modifier = modifierForParent,
        model = linkToImage,
        content = {
            when (state) {
                is RequestState.Failure -> ErrorItem()
                is RequestState.Loading -> LoadingItem()
                is RequestState.Success -> Image(
                    painter = painter,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = modifierForImage
                )
            }
        }
    )
}