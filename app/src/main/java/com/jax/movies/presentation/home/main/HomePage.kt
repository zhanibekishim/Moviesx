package com.jax.movies.presentation.home.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jax.movies.R
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.entity.home.MoviesType
import com.jax.movies.navigation.main.BottomScreenItem
import com.jax.movies.presentation.components.BottomNavigationBar
import com.jax.movies.presentation.components.ErrorScreen
import com.jax.movies.presentation.components.MovieItem
import com.valentinilk.shimmer.shimmer

@Composable
fun HomePage(
    homeViewModel: HomeViewModel,
    currentRoute: String = BottomScreenItem.HomeScreen.route,
    modifier: Modifier = Modifier
) {                // OBSERVATION PATTERN
    // ROLE-> SUBSCRIBE TO STATE WHICH HOLDS DATA FETCHED FROM DATA SOURCE
    // DUTY -> OBSERVE ALL CHANGES FROM STATE AND THEN UPDATE UI
    val state = homeViewModel.homeScreenState.collectAsStateWithLifecycle()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onHomeClick = {},
                onProfileClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnSearchScreenClick)
                },
                onSearchClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnProfileScreenClick)
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(R.drawable.vector),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )
            Spacer(Modifier.height(24.dp))

            HandleMovieList(
                title = "Top 250 Movies",
                moviesState = state.value.top250MoviesState,
                moviesType = MoviesType.TOP_250_MOVIES,
                onListClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieTypeClick(it))
                },
                onMovieClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieClick(it))
                }
            )

            HandleMovieList(
                title = "Popular Movies",
                moviesState = state.value.popularMoviesState,
                moviesType = MoviesType.TOP_POPULAR_MOVIES,
                onListClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieTypeClick(it))
                },
                onMovieClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieClick(it))
                }
            )

            HandleMovieList(
                title = "Comics Theme",
                moviesState = state.value.comicsMoviesState,
                moviesType = MoviesType.COMICS_THEME,
                onListClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieTypeClick(it))
                },
                onMovieClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieClick(it))
                }
            )

            HandleMovieList(
                title = "Premiers",
                moviesState = state.value.premiersMoviesState,
                moviesType = MoviesType.PREMIERS,
                onListClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieTypeClick(it))
                },
                onMovieClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieClick(it))
                }
            )
        }
    }
}

@Composable
private fun HandleMovieList(
    title: String,
    moviesState: HomeScreenState.MoviesState,
    moviesType: MoviesType,
    onListClick: (MoviesType) -> Unit,
    onMovieClick: (Movie) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        when (moviesState) {
            is HomeScreenState.MoviesState.Loading -> {
                ShimmerLoadingItems()
            }

            is HomeScreenState.MoviesState.Success -> {
                LazyRowItem(
                    items = moviesState.movies,
                    onListClick = onListClick,
                    moviesType = moviesType,
                    title = title,
                    onMovieClick = onMovieClick
                )
            }

            is HomeScreenState.MoviesState.Error -> ErrorScreen(errorMessage = moviesState.message)
            HomeScreenState.MoviesState.Initial -> Unit
        }
    }
}

@Composable
private fun LazyRowItem(
    items: List<Movie>,
    title: String,
    moviesType: MoviesType,
    onMovieClick: (Movie) -> Unit,
    onListClick: (MoviesType) -> Unit
) {
    var wrapped by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            item {
                Text(
                    text = stringResource(R.string.seeAll),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    modifier = Modifier
                        .clickable { onListClick(moviesType) }
                )
            }
        }

        val displayedItems = if (wrapped) items.take(items.size / 2) else items
        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(displayedItems) { movie ->
                MovieItem(
                    movie = movie,
                    onMovieClick = { onMovieClick(movie) }
                )
            }
            item {
                val icon = if (wrapped) R.drawable.arrow_right else R.drawable.arrowleft
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { wrapped = !wrapped }
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "See more",
                        tint = Color.Blue,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ShimmerLoadingItems(
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        items(10) {
            Box(
                modifier = Modifier
                    .size(111.dp, 156.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
                    .shimmer()
            )
        }
    }
}

