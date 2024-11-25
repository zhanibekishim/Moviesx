package com.jax.movies.presentation.search

import android.transition.Slide
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.R
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.navigation.main.BottomScreenItem
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.presentation.components.BottomNavigationBar
import com.jax.movies.presentation.components.MovieItem
import com.jax.movies.presentation.components.MyTopAppBar
import com.jax.movies.utils.DefaultLists.defaultFilterTypes
import com.jax.movies.utils.DefaultLists.defaultGenres
import com.jax.movies.utils.DefaultLists.defaultMovieTypes
import com.jax.movies.utils.DefaultLists.yearsList

@Composable
fun SearchScreen(
    navigationState: NavigationState
) {
    val currentRoute =
        navigationState.navHostController.currentBackStackEntry?.destination?.route.toString()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onHomeClick = {
                    navigationState.navigateTo(BottomScreenItem.HomeScreen.route)
                },
                onProfileClick = {
                    navigationState.navigateTo(BottomScreenItem.ProfileScreen.route)
                },
                onSearchClick = {
                    navigationState.navigateTo(BottomScreenItem.SearchScreen.route)
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center
        ) {
            Text(text = "Search Screen")
        }
    }
}

@Composable
private fun SearchScreenContent(
    onMovieClick: (Movie) -> Unit,
    movies: List<Movie>,
    onSearchClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    MySearchBar(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    leadingIcon: Int,
    placeHolderText: String,
    onSearchClick: (String) -> Unit,
    trailingIcon1: Int? = null,
    trailingIcon2: Int? = null,
    modifier: Modifier = Modifier
) {
    var searchValue by remember {
        mutableStateOf("")
    }
    SearchBar(
        query = searchValue,
        onQueryChange = {
            searchValue = it
        },
        onSearch = onSearchClick,
        active = true,
        onActiveChange = {},
        leadingIcon = {
            IconButton(onClick = { onSearchClick(searchValue) }) {
                Icon(
                    painter = painterResource(leadingIcon),
                    contentDescription = "search",
                    modifier = Modifier.size(12.dp)
                )
            }
        },
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    trailingIcon1?.let { painterResource(id = it) }?.let {
                        Icon(
                            painter = it,
                            contentDescription = "sort",
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
                IconButton(onClick = {}) {
                    trailingIcon2?.let { painterResource(id = it) }?.let {
                        Icon(
                            painter = it,
                            contentDescription = "sort",
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        },
        placeholder = {
            Text(
                text = placeHolderText,
                color = Color(0xFF838390),
                fontSize = 14.sp
            )
        },
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(68.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomEnd = 16.dp,
                    bottomStart = 16.dp
                )
            ),
        content = {}
    )
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
fun SortTypeScreen(
    types: List<String> = defaultGenres,
    topBarTitle: String = "Жанр",
    searchBarTitle: String = "Введите жанр",
    onTypeClick: (String) -> Unit = {},
    onClickBack: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                onNavClick = onClickBack,
                navIcon = R.drawable.icon_back,
                title = topBarTitle
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            MySearchBar(
                leadingIcon = R.drawable.icon_search,
                placeHolderText = searchBarTitle,
                onSearchClick = {},
                modifier = Modifier.padding(16.dp)
            )
            ChooseTypeSection(
                types = types,
                onTypeClick = onTypeClick
            )
        }
    }
}

@Composable
fun ChooseTypeSection(
    onTypeClick: (String) -> Unit,
    types: List<String>
) {
    Column {
        types.forEach {
            Text(
                text = it,
                fontSize = 16.sp,
                color = Color(0xFF272727),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clickable { onTypeClick(it) }
            )
        }
    }
}


@Composable
fun PeriodSection(
    onPeriodChooseClick: (Int) -> Unit,
    onClickBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                onNavClick = onClickBack,
                navIcon = R.drawable.icon_back,
                title = "Период"
            )
        }
    ) { padding ->
        var chosenYear by remember {
            mutableIntStateOf(1998)
        }
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            DatePicker(
                title = "Искать в период с",
                years = yearsList,
                onYearClick = {
                    chosenYear = it
                },
                onBackYearClick = {},
                onNextYearClick = {}
            )
            DatePicker(
                title = "Искать в период до",
                years = yearsList,
                onYearClick = {
                    chosenYear = it
                },
                onBackYearClick = {},
                onNextYearClick = {}
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        onPeriodChooseClick(chosenYear)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D3BFF)),
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Выбрать",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DatePicker(
    title: String,
    years: List<String>,
    onBackYearClick: () -> Unit,
    onNextYearClick: () -> Unit,
    onYearClick: (Int) -> Unit,
    yearFrom: Int = 1998,
    yearTo: Int = 2009,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(16.dp)) {
        Text(
            text = title,
            color = Color(0xFF838390),
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Box(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(0xFF272727)
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = "$yearFrom-$yearTo",
                        color = Color(0xFF3D3BFF),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500
                    )
                    IconMix(
                        firstIconClick = onBackYearClick,
                        secondIconClick = onNextYearClick
                    )
                }
                YearsGrid(
                    years = years,
                    onYearClick = onYearClick,
                    modifier = Modifier.padding(horizontal = 40.dp, vertical = 10.dp)
                )
            }
        }
    }
}

@Composable
fun IconMix(
    firstIconClick: () -> Unit,
    secondIconClick: () -> Unit,
    firstIcon: Int = R.drawable.icon_before_year,
    secondIcon: Int = R.drawable.icon_next_year,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        IconButton(onClick = firstIconClick) {
            Icon(
                painter = painterResource(id = firstIcon),
                contentDescription = "before year",
                modifier = Modifier.size(15.dp)
            )
        }
        IconButton(onClick = secondIconClick) {
            Icon(
                painter = painterResource(id = secondIcon),
                contentDescription = "before year",
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

@Composable
fun YearsGrid(
    onYearClick: (Int) -> Unit,
    years: List<String>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.padding(start = 12.dp)
    ) {
        years.forEach {
            item {
                Text(
                    text = it,
                    color = Color(0xFF272727),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onYearClick(it.toInt()) }
                )
            }
        }
    }
}

@Composable
fun SearchSettings() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Search Settings")
    }
}

@Preview
@Composable
fun FilterComponent() {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
    ){
        FilterSection(
            title = stringResource(R.string.show),
            types = defaultMovieTypes
        )
        FilterTypesSection()
        FilterSection(
            title = stringResource(R.string.sort),
            types = defaultMovieTypes
        )
    }
}

@Composable
fun FilterSection(
    title: String,
    types: List<String>,
    chosenContainerColor: Color = Color(0xFF3D3BFF),
    unChosenContainerColor: Color = Color.White,
    chosenTextColor: Color = Color.White,
    unChosenTextColor: Color = Color.Black,
    modifier: Modifier = Modifier
) {
    var chosenType by remember {
        mutableStateOf("")
    }
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
                        .clickable { chosenType = type }
                ){
                    Text(
                        text = type,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        color = textColor)
                }
            }
        }
    }
}

@Composable
fun FilterTypesSection(
    types: List<Pair<String, String>> = defaultFilterTypes
) {
    Column {
        types.forEachIndexed {index, item->
           Column {
               Row(
                   horizontalArrangement = Arrangement.SpaceBetween,
                   verticalAlignment = Alignment.CenterVertically,
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(26.dp)
               ) {
                   Text(
                       text = item.first,
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
               if(index == types.size-1){
                   BlueSlider()
               }
           }
        }
    }
}
@Composable
fun BlueSlider() {
    var sliderValue by remember { mutableFloatStateOf(5f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
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





















