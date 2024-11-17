package com.jax.movies.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jax.movies.presentation.detail.actor.ActorDetailViewModel
import com.jax.movies.presentation.detail.actor.ActorScreenIntent
import com.jax.movies.presentation.detail.filmography.FilmographyScreenIntent
import com.jax.movies.presentation.detail.filmography.FilmographyViewModel
import com.jax.movies.presentation.detail.gallery.GalleryScreenIntent
import com.jax.movies.presentation.detail.gallery.GalleryViewModel
import com.jax.movies.presentation.detail.movie.MovieDetailViewModel
import com.jax.movies.presentation.detail.movie.MovieScreenIntent
import com.jax.movies.presentation.detail.movies.MoviesScreenIntent
import com.jax.movies.presentation.detail.movies.MoviesViewModel
import com.jax.movies.presentation.common.BottomNavigationBar
import com.jax.movies.presentation.common.BottomScreenItem
import com.jax.movies.presentation.home.main.HomePage
import com.jax.movies.presentation.home.main.HomeScreenIntent
import com.jax.movies.presentation.home.main.HomeViewModel
import com.jax.movies.presentation.profile.ProfileScreen
import com.jax.movies.presentation.search.SearchScreen

@Composable
fun MainNavGraph(
    navigationState: NavigationState,
    paddingValues: PaddingValues
) {
    val homeViewModel: HomeViewModel = viewModel()
    val homeScreenIntent =
        homeViewModel.homeNavigationChannel.collectAsStateWithLifecycle(HomeScreenIntent.Default)
    LaunchedEffect(homeScreenIntent.value) {
        when (val currentState = homeScreenIntent.value) {
            is HomeScreenIntent.Default -> {}
            is HomeScreenIntent.OnMovieClick -> {
                Log.d("collected movie", currentState.movie.toString())
                navigationState.navigateToMovieDetailScreen(
                    movie = currentState.movie,
                    backRoute = BottomScreenItem.HomeScreen.route
                )
            }

            is HomeScreenIntent.OnMovieTypeClick -> {
                Log.d("collected movieType", currentState.movieType.toString())
                navigationState.navigateToMoviesScreen(currentState.movieType)
            }
        }
    }

    val movieViewModel: MovieDetailViewModel = viewModel()
    val movieDetailIntent =
        movieViewModel.movieNavigationChannel.collectAsStateWithLifecycle(MovieScreenIntent.Default)

   LaunchedEffect(movieDetailIntent.value) {
       when (val currentIntent = movieDetailIntent.value) {
           is MovieScreenIntent.OnActorClick -> {
               navigationState.navigateToActorScreen(currentIntent.actor)
           }

           is MovieScreenIntent.OnBackClicked -> {
               navigationState.navHostController.popBackStack()
           }

           is MovieScreenIntent.OnGalleryClick -> {
               navigationState.navigateToGalleryScreen(currentIntent.movie)
           }

           is MovieScreenIntent.OnMovieClick -> {
               navigationState.navigateToMovieDetailScreen(
                   movie = currentIntent.movie,
                   backRoute = BottomScreenItem.HomeScreen.route
               )
           }

           is MovieScreenIntent.Default -> {}
           is MovieScreenIntent.OnBlindEyeClick -> {}
           is MovieScreenIntent.OnFavouriteClick -> {}
           is MovieScreenIntent.OnLickClick -> {}
           is MovieScreenIntent.OnMoreClick -> {}
           is MovieScreenIntent.OnShareClick -> {}
       }
   }
    val moviesViewModel: MoviesViewModel = viewModel()
    val moviesScreenIntent =
        moviesViewModel.moviesNavigationChannel.collectAsStateWithLifecycle(MoviesScreenIntent.Default)
    LaunchedEffect(moviesScreenIntent.value) {
        when (val currentIntent = moviesScreenIntent.value) {
            is MoviesScreenIntent.Default -> {}
            is MoviesScreenIntent.OnClickBack -> {
                navigationState.navHostController.popBackStack()
            }

            is MoviesScreenIntent.OnMovieClick -> {
                navigationState.navigateToMovieDetailScreen(movie = currentIntent.movie)
            }

        }
    }
    val galleryViewModel: GalleryViewModel = viewModel()
    val galleriesIntent =
        galleryViewModel.galleryNavigationChannel.collectAsStateWithLifecycle(GalleryScreenIntent.Default)
   LaunchedEffect(galleriesIntent.value) {
       when (galleriesIntent.value) {
           GalleryScreenIntent.Default -> {}
           GalleryScreenIntent.OnClickBack -> {
               navigationState.navHostController.popBackStack()
           }
       }
   }
    val filmographyViewModel: FilmographyViewModel = viewModel()
    val filmographyIntent =
        filmographyViewModel.filmographyNavigationChannel.collectAsStateWithLifecycle(
            FilmographyScreenIntent.Default
        )
   LaunchedEffect(filmographyIntent.value) {
       when (val currentIntent = filmographyIntent.value) {
           is FilmographyScreenIntent.Default -> {}
           is FilmographyScreenIntent.OnClickBack -> {
               navigationState.navHostController.popBackStack()
           }

           is FilmographyScreenIntent.OnMovieClick -> {
               navigationState.navigateToMovieDetailScreen(currentIntent.movie)
           }
       }
   }

    val actorViewModel: ActorDetailViewModel = viewModel()
    val actorDetailIntent =
        actorViewModel.actorNavigationChannel.collectAsStateWithLifecycle(ActorScreenIntent.Default)

    LaunchedEffect(actorDetailIntent.value) {
        when (val currentIntent = actorDetailIntent.value) {
            is ActorScreenIntent.Default -> {}
            is ActorScreenIntent.OnFilmographyClick -> {
                navigationState.navigateToFilmographyScreen(currentIntent.actor)
            }

            is ActorScreenIntent.OnMovieClick -> {
                navigationState.navigateToMovieDetailScreen(currentIntent.movie)
            }
            is ActorScreenIntent.OnClickBack -> {
                navigationState.navHostController.popBackStack()
            }
        }
    }
    NavHost(
        navController = navigationState.navHostController,
        route = GRAPH.MAIN_GRAPH,
        startDestination = BottomScreenItem.HomeScreen.route
    ) {
        composable(BottomScreenItem.HomeScreen.route) {
            HomePage(
                paddingValues = paddingValues,
                homeViewModel = homeViewModel
            )
        }
        composable(BottomScreenItem.SearchScreen.route) { SearchScreen(paddingValues) }
        composable(BottomScreenItem.ProfileScreen.route) { ProfileScreen(paddingValues) }
        detailScreen(
            actorViewModel = actorViewModel,
            moviesViewModel = moviesViewModel,
            galleryViewModel = galleryViewModel,
            filmographyViewModel = filmographyViewModel,
            movieDetailViewModel = movieViewModel
        )
    }
}

@Composable
fun MainPages() {
    val navigationState = rememberNavigationState()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navigationState.navHostController,
                onHomeClick = {
                    navigationState.navigateTo(BottomScreenItem.HomeScreen.route)
                },
                onProfileClick = {
                    navigationState.navigateTo(BottomScreenItem.ProfileScreen.route)
                },
                onSearchClick = {
                    navigationState.navigateTo(BottomScreenItem.SearchScreen.route)
                }
            )
        }
    ) { paddingValues ->
        MainNavGraph(
            navigationState = navigationState,
            paddingValues = paddingValues
        )
    }
}