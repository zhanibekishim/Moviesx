package com.jax.movies.presentation.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.R
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.navigation.main.BottomScreenItem
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.presentation.components.BottomNavigationBar
import com.jax.movies.presentation.components.RelatedMoviesSection
import com.jax.movies.utils.DefaultLists.defaultCollectionTypes

@Composable
fun ProfileScreen(
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
            Text(text = "Profile Screen")
        }
    }
}

@Composable
private fun ProfileMainContent(
    onMovieClick: (Movie) -> Unit,
    seenMovies: List<Movie>,
    interestedMovies: List<Movie>,
    collection: List<CollectionItem>,
    onCollectionClick: (CollectionItem) -> Unit,
    deleteMoviesIconClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.padding(horizontal = 26.dp)
    ) {
        RelatedMoviesSection(
            title = "Посмотрено",
            onMovieClick = onMovieClick,
            countOrAll = seenMovies.size.toString(),
            relatedMovies = seenMovies,
            deleteMoviesIcon = true,
            deleteMoviesIconClicked = deleteMoviesIconClicked,
            modifier = modifier
        )
        CollectionComponent(
            collection = collection,
            onCollectionClick = onCollectionClick,
            onAddClicked = {}
        )
        RelatedMoviesSection(
            title = "Вам было интересно",
            onMovieClick = onMovieClick,
            countOrAll = interestedMovies.size.toString(),
            relatedMovies = interestedMovies,
            deleteMoviesIcon = true,
            modifier = modifier
        )
    }
}

@Composable
private fun CollectionComponent(
    collection: List<CollectionItem>,
    onCollectionClick: (CollectionItem) -> Unit,
    onAddClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Коллекции",
            fontWeight = FontWeight.W600,
            fontSize = 18.sp,
            color = Color(0xFF272727)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onAddClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.icons_add),
                    contentDescription = "add"
                )
            }
            Text(
                text = "Создать свою коллекцию",
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                color = Color(0xFF272727),
                modifier = Modifier.weight(1f)
            )
        }
        CollectionSection(
            collection = collection,
            onCollectionClick = onCollectionClick
        )
    }
}

@Composable
private fun CollectionSection(
    collection: List<CollectionItem>,
    onCollectionClick: (CollectionItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(collection) {
            CollectionTypeBox(
                collectionItem = it,
                onCollectionClick = onCollectionClick
            )
        }
    }
}

@Composable
private fun CollectionTypeBox(
    onCollectionClick: (CollectionItem) -> Unit,
    collectionItem: CollectionItem,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .size(146.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color(0xFF000000)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(22.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = { onCollectionClick(collectionItem) }) {
                Icon(
                    painter = painterResource(id = collectionItem.collectionTypeIcon),
                    contentDescription = collectionItem.title,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = collectionItem.title,
                fontWeight = FontWeight.W400,
                fontSize = 12.sp,
                color = Color(0xFF272727)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF3D3BFF))
                    .padding(4.dp)
            ) {
                Text(
                    text = collectionItem.count.toString(),
                    fontWeight = FontWeight.W500,
                    fontSize = 8.sp,
                    color = Color(0xFFFFFFFF),
                    modifier = Modifier
                        .width(20.dp)
                        .height(10.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}




































