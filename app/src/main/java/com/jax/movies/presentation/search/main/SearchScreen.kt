package com.jax.movies.presentation.search.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jax.movies.R
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.components.BottomNavigationBar
import com.jax.movies.presentation.components.LoadingScreen
import com.jax.movies.presentation.components.MovieItem
import com.jax.movies.presentation.components.SearchBar
import com.jax.movies.presentation.search.setting.FilterType
import com.jax.movies.utils.DefaultLists.defaultFilterTypes

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    currentRoute: String
) {
    val state by searchViewModel.screenState.collectAsStateWithLifecycle()

    when (val currentState = state) {

        is SearchScreenState.Error -> {
            SearchScreenNotFound()
        }

        is SearchScreenState.Loading -> {
            LoadingScreen()
        }

        is SearchScreenState.Initial -> {
            SearchBar(
                trailingIcon1 = R.drawable.icon_devider,
                trailingIcon2 = R.drawable.icon_sort,
                onSearchClick = {
                    searchViewModel.handleIntent(SearchScreenIntent.OnValueChange(it))
                },
                onTrailingIcon2Click = {
                    searchViewModel.handleEvent(SearchScreenIntent.Event.OnFilterClick)
                }
            )
        }

        is SearchScreenState.Success -> {
            SearchScreenContent(
                onSearchClick = {
                    searchViewModel.handleIntent(SearchScreenIntent.OnValueChange(it))
                },
                onMovieClick = {
                    searchViewModel.handleEvent(SearchScreenIntent.Event.OnMovieClick(it))
                },
                onProfileClick = {
                    searchViewModel.handleEvent(SearchScreenIntent.Event.OnProfileClick)
                },
                onHomeClick = {
                    searchViewModel.handleEvent(SearchScreenIntent.Event.OnHomeClick)
                },
                currentRoute = currentRoute,
                movies = currentState.movies
            )
        }
    }
}

@Composable
private fun SearchScreenContent(
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    currentRoute: String,
    onMovieClick: (Movie) -> Unit,
    onSearchClick: (String) -> Unit,
    movies: List<Movie>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onHomeClick = onHomeClick,
                onProfileClick = onProfileClick,
                onSearchClick = {},
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            SearchBar(
                leadingIcon = R.drawable.icon_search,
                placeHolderText = stringResource(R.string.searchbarPlaceHolder),
                trailingIcon1 = R.drawable.icon_devider,
                trailingIcon2 = R.drawable.icon_sort,
                onSearchClick = onSearchClick
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier
            ) {
                items(movies) { movie ->
                    MovieItem(
                        movie = movie,
                        onMovieClick = onMovieClick,
                        ratingPosition = Alignment.TopStart,
                        vertically = false,
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchScreenNotFound() {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = stringResource(R.string.not_found_search_bar))
    }
}

@Composable
fun <T> FilterSection(
    chosenType: T,
    onChoose: (T) -> Unit,
    types: List<T>,
    title: String,
    chosenContainerColor: Color = Color(0xFF3D3BFF),
    unChosenContainerColor: Color = Color.White,
    chosenTextColor: Color = Color.White,
    unChosenTextColor: Color = Color.Black,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = title,
            color = Color(0xFF838390),
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(0xFF272727)
                    ),
                    shape = RoundedCornerShape(56.dp)
                )
                .padding(4.dp)
        ) {
            types.forEach { type ->
                val bgColor =
                    if (chosenType == type) chosenContainerColor else unChosenContainerColor
                val textColor = if (chosenType == type) chosenTextColor else unChosenTextColor
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(bgColor)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { onChoose(type) }
                ) {
                    Text(
                        text = type.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        color = textColor
                    )
                }
            }
        }
    }
}

@Composable
fun FilterTypesSection(
    onChoose: (FilterType) -> Unit,
    types: List<Pair<FilterType, String>> = defaultFilterTypes
) {
    Column {
        types.forEachIndexed { index, item ->
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(26.dp)
                        .clickable {
                            onChoose(item.first)
                        }
                ) {
                    Text(
                        text = item.first.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF272727)
                    )
                    Text(
                        text = item.second,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF838390)
                    )
                }
                if (index == types.size - 1) {
                    RatingSlider(
                        onChoose = { value ->
                            FilterType.RATING.updateDouble(value)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun RatingSlider(
    onChoose: (Double) -> Unit
) {
    var sliderValue by remember { mutableFloatStateOf(5f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = sliderValue,
            onValueChange = {
                sliderValue = it
                onChoose(it.toDouble())
            },
            valueRange = 1f..10f,
            steps = 8,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.Blue
            ),
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Value: ${sliderValue.toInt()}",
            color = Color.Blue
        )
    }
}





















