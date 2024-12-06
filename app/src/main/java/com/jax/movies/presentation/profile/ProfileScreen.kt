package com.jax.movies.presentation.profile

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.R
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.components.BottomNavigationBar
import com.jax.movies.presentation.components.ErrorScreen
import com.jax.movies.presentation.components.MovieCollectionItem
import com.jax.movies.presentation.components.NewCollectionDialog
import com.jax.movies.presentation.components.RelatedMoviesSection
import com.jax.movies.presentation.theme.Blue1

@Composable
fun ProfileScreen(
    profileScreenViewModel: ProfileScreenViewModel,
    currentRoute: String
) {
    val state by profileScreenViewModel.state.observeAsState(ProfileScreenState.Initial)

    when (val currentState = state) {
        is ProfileScreenState.Initial -> {
            Text("Loading...")
            Log.d("dasdasdasdasdsadas", "ProfileScreen: Initial")
        }

        is ProfileScreenState.Error -> ErrorScreen(currentState.message)
        is ProfileScreenState.Success -> {
            ProfileMainContent(
                currentRoute = currentRoute,
                onSearchClick = {
                    profileScreenViewModel.handleEvent(ProfileScreenIntent.Event.OnSearchClick)
                },
                onHomeClicked = {
                    profileScreenViewModel.handleEvent(ProfileScreenIntent.Event.OnHomeClick)
                },
                onMovieClick = {
                    profileScreenViewModel.handleEvent(ProfileScreenIntent.Event.OnMovieClick(it))
                },
                deleteMoviesIconClicked = {
                    profileScreenViewModel.handleEvent(ProfileScreenIntent.Event.OnDeleteClick(it))
                },
                seenMovies = currentState.seenMovies,
                favouriteMovies = currentState.favouriteMovies,
                collection = currentState.collection.toCollectionItemList(),
                onAddCollection = {
                    profileScreenViewModel.handleIntent(ProfileScreenIntent.OnAddNewCollection(it))
                },
                onDeleteCollection = {
                    profileScreenViewModel.handleIntent(ProfileScreenIntent.OnDeleteCollection(it))
                },
                onCollectionClick = {},
            )
        }
    }
}

private fun List<MovieCollectionItem>.toCollectionItemList(): List<CollectionItem> {
    return this.map { item ->
        val icon = when {
            item.name == "Любимое" -> R.drawable.icon_liked
            else -> R.drawable.icon_favourite
        }
        CollectionItem(
            title = item.name,
            count = item.count,
            collectionTypeIcon = icon
        )
    }
}

@Composable
private fun ProfileMainContent(
    currentRoute: String,
    onDeleteCollection: (MovieCollectionItem) -> Unit,
    onAddCollection: (String) -> Unit,
    onSearchClick: () -> Unit,
    onHomeClicked: () -> Unit,
    onMovieClick: (Movie) -> Unit,
    seenMovies: List<Movie>,
    favouriteMovies: List<Movie>,
    collection: List<CollectionItem>,
    onCollectionClick: (CollectionItem) -> Unit,
    deleteMoviesIconClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onHomeClick = onHomeClicked,
                onSearchClick = onSearchClick,
                onProfileClick = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 26.dp)
                .verticalScroll(rememberScrollState())
        ) {

            RelatedMoviesSection(
                title = "Посмотрено",
                onMovieClick = onMovieClick,
                countOrAll = favouriteMovies.size.toString(),
                relatedMovies = favouriteMovies,
                deleteMoviesIcon = true,
                deleteMoviesIconClicked = { deleteMoviesIconClicked("Вам было интересно") },
                modifier = modifier
            )
            CollectionComponent(
                collection = collection,
                onCollectionClick = onCollectionClick,
                onAddCollection = onAddCollection,
                onDeleteCollection = onDeleteCollection,
                countLickedFavourite = seenMovies.size to favouriteMovies.size
            )
            RelatedMoviesSection(
                title = "Вам было интересно",
                onMovieClick = onMovieClick,
                countOrAll = seenMovies.size.toString(),
                relatedMovies = seenMovies,
                deleteMoviesIcon = true,
                deleteMoviesIconClicked = { deleteMoviesIconClicked("Посмотрено") },
                modifier = modifier
            )
        }
    }
}

@Composable
private fun CollectionComponent(
    countLickedFavourite:Pair<Int,Int>,
    onDeleteCollection: (MovieCollectionItem) -> Unit,
    onAddCollection: (String) -> Unit,
    collection: List<CollectionItem>,
    onCollectionClick: (CollectionItem) -> Unit,
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
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
            IconButton(onClick = { showDialog = !showDialog }) {
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
            onCollectionClick = onCollectionClick,
            onDeleteCollection = onDeleteCollection,
            countLickedFavourite = countLickedFavourite
        )
    }
    if (showDialog) {
        NewCollectionDialog(
            collection = collection,
            onDismiss = { showDialog = !showDialog },
            onConfirm = onAddCollection
        )
    }
}

@Composable
private fun CollectionSection(
    countLickedFavourite:Pair<Int,Int>,
    onDeleteCollection: (MovieCollectionItem) -> Unit,
    collection: List<CollectionItem>,
    onCollectionClick: (CollectionItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        collection.chunked(2).forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEachIndexed { index,item ->
                    if(item.title == "Посмотрено"){
                        item.default = false
                        item.count = countLickedFavourite.first
                    }
                    if(item.title == "Вам было интересно"){
                        item.default = false
                        item.count = countLickedFavourite.second
                    }
                    CollectionTypeBox(
                        collectionItem = item,
                        onCollectionClick = onCollectionClick,
                        onDeleteClick = onDeleteCollection,
                        modifier = Modifier.weight(1f)
                    )
                }
                repeat(2 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun CollectionTypeBox(
    onCollectionClick: (CollectionItem) -> Unit,
    onDeleteClick: (MovieCollectionItem) -> Unit,
    collectionItem: CollectionItem,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(146.dp)
            .border(
                border = BorderStroke(1.dp, color = Color(0xFF000000)),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if(collectionItem.default){
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(
                        onClick = {
                            val item = MovieCollectionItem(name = collectionItem.title, count = collectionItem.count)
                            onDeleteClick(item)
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_delete),
                            contentDescription = "Delete ${collectionItem.title}"
                        )
                    }
                }
            }

            IconButton(
                onClick = { onCollectionClick(collectionItem) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = collectionItem.collectionTypeIcon),
                    contentDescription = collectionItem.title,
                    modifier = Modifier.size(48.dp)
                )
            }

            Text(
                text = collectionItem.title,
                fontWeight = FontWeight.W400,
                fontSize = 12.sp,
                color = Color(0xFF272727),
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = collectionItem.count.toString(),
                fontWeight = FontWeight.W500,
                fontSize = 15.sp,
                color = Color.White,
                modifier = Modifier
                    .size(25.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Blue1)
                    .padding(start = 4.dp)
            )
        }
    }
}





































