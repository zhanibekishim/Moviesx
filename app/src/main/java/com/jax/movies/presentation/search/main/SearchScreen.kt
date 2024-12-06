package com.jax.movies.presentation.search.main

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jax.movies.R
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.components.BottomNavigationBar
import com.jax.movies.presentation.components.MovieItem
import com.jax.movies.presentation.components.CustomSearchBar
import com.jax.movies.presentation.search.setting.FilterType

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    currentRoute: String,
    sortBy: SortBy
) {
    val state by searchViewModel.screenState.collectAsStateWithLifecycle(
        initialValue = SearchScreenState.Initial
    )
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onProfileClick = {
                    searchViewModel.handleEvent(SearchScreenIntent.Event.OnProfileClick)
                },
                onHomeClick = {
                    searchViewModel.handleEvent(SearchScreenIntent.Event.OnHomeClick)
                },
                onSearchClick = {},
            )
        }
    ) { innerPadding ->
        when (val currentState = state) {
            is SearchScreenState.Error -> {
                SearchScreenNotFound(
                    onSearchClick = {},
                    onNewQuery = {
                        searchViewModel.handleIntent(SearchScreenIntent.OnValueChange(it, sortBy))
                    },
                    onTrailingIcon2Click = {
                        searchViewModel.handleEvent(SearchScreenIntent.Event.OnFilterClick)
                    },
                    query = currentState.query
                )
            }

            is SearchScreenState.Loading -> {
                SearchLoadingScreen(
                    onTrailingIcon2Click = {
                        searchViewModel.handleEvent(SearchScreenIntent.Event.OnFilterClick)
                    },
                    onSearchClick = {
                        searchViewModel.handleIntent(SearchScreenIntent.OnValueChange(it, sortBy))
                    },
                    onNewQuery = {
                        searchViewModel.handleEvent(SearchScreenIntent.Event.OnFilterClick)
                    },
                    currentQuery = currentState.query
                )
            }

            is SearchScreenState.Initial -> {
                CustomSearchBar(
                    onSearchClick = {
                        searchViewModel.handleIntent(SearchScreenIntent.OnValueChange(it, sortBy))
                    },
                    onNewQuery = {
                        searchViewModel.handleIntent(SearchScreenIntent.OnValueChange(it, sortBy))
                    },
                    onTrailingIcon2Click = {
                        searchViewModel.handleEvent(SearchScreenIntent.Event.OnFilterClick)
                    },
                    trailingIcon1 = R.drawable.icon_devider,
                    trailingIcon2 = R.drawable.icon_sort,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is SearchScreenState.Success -> {
                if(currentState.movies.isNotEmpty()){
                    SearchScreenContent(
                        onSearchClick = {
                            searchViewModel.handleIntent(SearchScreenIntent.OnValueChange(it, sortBy))
                        },
                        onNewQuery = {
                            searchViewModel.handleIntent(SearchScreenIntent.OnValueChange(it, sortBy))
                        },
                        onMovieClick = {
                            searchViewModel.handleEvent(SearchScreenIntent.Event.OnMovieClick(it))
                        },
                        movies = currentState.movies,
                        currentQuery = currentState.query,
                        modifier = Modifier.padding(innerPadding)
                    )
                }else{
                    SearchScreenNotFound(
                        onSearchClick = {
                            searchViewModel.handleIntent(SearchScreenIntent.OnValueChange(it, sortBy))
                        },
                        onNewQuery = {
                            searchViewModel.handleIntent(SearchScreenIntent.OnValueChange(it, sortBy))
                        },
                        onTrailingIcon2Click = {
                            searchViewModel.handleEvent(SearchScreenIntent.Event.OnFilterClick)
                        },
                        query = currentState.query
                    )
                }
            }
        }
    }

}

@Composable
private fun SearchScreenContent(
    onNewQuery: (String) -> Unit,
    onMovieClick: (Movie) -> Unit,
    onSearchClick: (String) -> Unit,
    movies: List<Movie>,
    currentQuery: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        CustomSearchBar(
            currentQuery = currentQuery,
            leadingIcon = R.drawable.icon_search,
            placeHolderText = stringResource(R.string.searchbarPlaceHolder),
            trailingIcon1 = R.drawable.icon_devider,
            trailingIcon2 = R.drawable.icon_sort,
            onSearchClick = onSearchClick,
            onNewQuery = { onNewQuery(it) }
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

@Composable
fun SearchScreenNotFound(
    onNewQuery: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onTrailingIcon2Click: () -> Unit,
    query: String
) {
    Column {
        CustomSearchBar(
            currentQuery = query,
            trailingIcon1 = R.drawable.icon_devider,
            trailingIcon2 = R.drawable.icon_sort,
            onTrailingIcon2Click = onTrailingIcon2Click,
            onSearchClick = onSearchClick,
            onNewQuery = onNewQuery,
        )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(text = stringResource(R.string.not_found_search_bar))
        }
    }
}

@Composable
fun FilterTypesSection(
    onChoose: (FilterType) -> Unit,
    onRatingChoose: (Double) -> Unit,
    types: List<FilterType>
) {
    Column {
        types.forEachIndexed { _, item ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(26.dp)
                    .clickable { onChoose(item) }
            ) {
                when (item) {
                    is FilterType.Country -> {
                        FilterItem(name = "Город", secondName = item.country)
                    }

                    is FilterType.Genre -> {
                        FilterItem(name = "Жанр", secondName = item.genre)
                    }

                    is FilterType.Period -> {
                        FilterItem(name = "Год", secondName = "С ${item.from}" + "до ${item.to}")
                    }

                    is FilterType.Rating -> {
                        RatingSlider(onChoose = onRatingChoose)
                    }
                }
            }
            HorizontalDivider()
        }
    }
}

@Composable
private fun FilterItem(
    name: String,
    secondName: String
) {
    Text(
        text = name,
        fontSize = 17.sp,
        fontWeight = FontWeight.W500,
        color = Color(0xFF272727)
    )
    Text(
        text = secondName,
        fontSize = 17.sp,
        fontWeight = FontWeight.W400,
        color = Color(0xFF838390)
    )
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
private fun RatingSlider(
    onChoose: (Double) -> Unit
) {
    var sliderValue by remember { mutableFloatStateOf(5f) }
    var sliderWidth by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Рейтинг",
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xFF272727)
            )
            Text(
                text = if (sliderValue == 1f) "любой" else sliderValue.toInt().toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xFF838390)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
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
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        sliderWidth = coordinates.size.width
                    }
            )
            Text(
                text = sliderValue.toInt().toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .offset(
                        x = calculateThumbPosition(sliderValue, 1f..10f, sliderWidth)
                    )
                    .align(Alignment.CenterStart)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "1",
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xFF838390)
            )
            Text(
                text = "10",
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xFF838390)
            )
        }
    }
}

@Composable
private fun calculateThumbPosition(
    sliderValue: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    sliderWidthPx: Int
): Dp {
    val density = LocalDensity.current
    val positionFraction = (sliderValue - valueRange.start) / (valueRange.endInclusive - valueRange.start)
    val thumbOffsetPx = sliderWidthPx * positionFraction

    return with(density) { thumbOffsetPx.toDp() }
}


@Composable
fun SearchLoadingScreen(
    onNewQuery: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onTrailingIcon2Click: () -> Unit,
    currentQuery: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomSearchBar(
            currentQuery = currentQuery,
            trailingIcon1 = R.drawable.icon_devider,
            trailingIcon2 = R.drawable.icon_sort,
            onTrailingIcon2Click = onTrailingIcon2Click,
            onSearchClick = onSearchClick,
            onNewQuery = onNewQuery,
        )
        CircularProgressIndicator(modifier = Modifier.weight(1f))
    }
}

















