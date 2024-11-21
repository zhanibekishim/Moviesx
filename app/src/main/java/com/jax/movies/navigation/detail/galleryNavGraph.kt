package com.jax.movies.navigation.detail

import android.os.Build
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.presentation.detail.gallery.GalleryScreen
import com.jax.movies.presentation.detail.gallery.GalleryScreenIntent
import com.jax.movies.presentation.detail.gallery.GalleryViewModel

fun NavGraphBuilder.galleryNavGraph(
   navigationState: NavigationState
){
    composable(
        route = Details.GalleryScreen.route,
        arguments = listOf(navArgument(Details.GALLERY_IMAGE_PARAMETER) {
            type = Movie.navType
        })
    ) { navBackStackEntry ->
        val galleryViewModel: GalleryViewModel = viewModel()
        val galleriesIntent =
            galleryViewModel.galleryNavigationChannel.collectAsStateWithLifecycle(
                GalleryScreenIntent.Default)
        LaunchedEffect(galleriesIntent.value) {
            when (galleriesIntent.value) {
                GalleryScreenIntent.Default -> {}
                GalleryScreenIntent.OnClickBack -> {
                    navigationState.navHostController.popBackStack()
                }
            }
        }
        val movie = navBackStackEntry.getMovie()
        GalleryScreen(movie = movie, galleryViewModel = galleryViewModel)
    }
}

private fun NavBackStackEntry.getMovie():Movie{
    return this.arguments?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            it.getParcelable(Details.GALLERY_IMAGE_PARAMETER, Movie::class.java)
        else it.getParcelable(Details.GALLERY_IMAGE_PARAMETER)
    } ?: throw IllegalStateException("Movie is missing")
}