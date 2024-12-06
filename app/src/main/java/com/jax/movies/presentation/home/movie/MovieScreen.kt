package com.jax.movies.presentation.home.movie

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jax.movies.R
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.components.ActorItem
import com.jax.movies.presentation.components.ErrorScreen
import com.jax.movies.presentation.components.FetchedImage
import com.jax.movies.presentation.components.LoadingScreen
import com.jax.movies.presentation.components.MovieCollectionItem
import com.jax.movies.presentation.components.MyTopAppBar
import com.jax.movies.presentation.components.RelatedMoviesSection
import com.jax.movies.presentation.components.ScreenWithBottomSheet
import com.jax.movies.presentation.components.SheetType
import com.jax.movies.presentation.components.StepTitle

@Composable
fun MovieContent(
    movieDetailViewModel: MovieDetailViewModel,
    movie: Movie
) {
    val state by movieDetailViewModel.state.collectAsStateWithLifecycle()
    val showBottomSheet = remember { mutableStateOf(false) }
    var currentSheetType by remember { mutableStateOf<SheetType>(SheetType.None) }

    when (val currentState = state) {
        is MovieDetailState.Initial -> {}
        is MovieDetailState.Loading -> LoadingScreen()
        is MovieDetailState.Error -> ErrorScreen(errorMessage = currentState.message)
        is MovieDetailState.Success -> {
            MainContent(
                movie = currentState.movie,
                isLicked = currentState.isLicked,
                isFavourite = currentState.isFavourite,
                onGalleryClick = {
                    movieDetailViewModel.handleIntent(
                        MovieScreenIntent.MovieScreenNavigationIntent.OnGalleryClick(
                            it
                        )
                    )
                },
                onActorClick = {
                    movieDetailViewModel.handleIntent(
                        MovieScreenIntent.MovieScreenNavigationIntent.OnActorClick(
                            it
                        )
                    )
                },
                onMovieClick = {
                    movieDetailViewModel.handleIntent(
                        MovieScreenIntent.MovieScreenNavigationIntent.OnMovieClick(
                            fromMovie = movie,
                            toMovie = it
                        )
                    )
                },
                onBackClicked = {
                    movieDetailViewModel.handleIntent(
                        MovieScreenIntent.MovieScreenNavigationIntent.OnBackClicked(
                            it
                        )
                    )
                },
                onLikeClicked = { likedMovie, isFavourite ->
                    showBottomSheet.value = true
                    currentSheetType = SheetType.Collections
                    movieDetailViewModel.handleIntent(
                        MovieScreenIntent.OnLickClick(
                            likedMovie,
                            isFavourite
                        )
                    )
                },
                onFavouriteClicked = { favouriteMovie, isFavourite ->
                    movieDetailViewModel.handleIntent(
                        MovieScreenIntent.OnFavouriteClick(
                            favouriteMovie,
                            isFavourite
                        )
                    )
                },
                onShareClicked = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnShareClick)
                },
                onBlindEyeClicked = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnBlindEyeClick)
                },
                onMoreClicked = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnMoreClick)
                },
                onAddCollection = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnNewCollectionAdd(it))
                },
                galleries = currentState.gallery,
                actors = currentState.actors,
                filmCrew = currentState.filmCrew,
                similarMovies = currentState.similarMovies,
                collection = currentState.collection,
                lickedCount = currentState.seenMovieItemCount,
                favouriteCount = currentState.favouriteMovieItemCount,
                onCheck = { movieCollectionItem, isChecked ->
                    movieDetailViewModel.handleIntent(
                        MovieScreenIntent.OnCheck(
                            movie,
                            movieCollectionItem,
                            isChecked
                        )
                    )
                }
            )
        }
    }
    LaunchedEffect(key1 = movie) {
        movieDetailViewModel.handleAction(MovieScreenAction.FetchMovieDetailInfo)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun MainContent(
    onCheck: (MovieCollectionItem, Boolean) -> Unit,
    onGalleryClick: (Movie) -> Unit,
    onActorClick: (Actor) -> Unit,
    onMovieClick: (Movie) -> Unit,
    onBackClicked: (Movie) -> Unit,
    onAddCollection: (String) -> Unit,
    onLikeClicked: (Movie, Boolean) -> Unit,
    onFavouriteClicked: (Movie, Boolean) -> Unit,
    onShareClicked: () -> Unit,
    onBlindEyeClicked: () -> Unit,
    onMoreClicked: () -> Unit,
    collection: List<MovieCollectionItem>,
    lickedCount:Int,
    favouriteCount:Int,
    isLicked: Boolean,
    isFavourite: Boolean,
    movie: Movie,
    galleries: List<GalleryImage>,
    actors: List<Actor>,
    filmCrew: List<Actor>,
    similarMovies: List<Movie>,
    modifier: Modifier = Modifier
) {
    val showBottomSheet = remember { mutableStateOf(false) }
    var currentSheetType by remember { mutableStateOf<SheetType>(SheetType.Collections) }

    var isFav by remember {
        mutableStateOf(isFavourite)
    }
    var isSeen by remember {
        mutableStateOf(isLicked)
    }
    val checked= mutableListOf<MovieCollectionItem>().apply {
        if (isFav) add(MovieCollectionItem(name = "Посмотрено", count = lickedCount))
        if (isSeen) add(MovieCollectionItem(name = "Вам было интересно", count = favouriteCount))
    }

    ScreenWithBottomSheet(
        topBar = {
            MyTopAppBar(
                onNavClick = { onBackClicked(movie) },
                navIcon = R.drawable.icon_back,
                title = ""
            )
        },
        movie = movie,
        collection = collection,
        sheetType = currentSheetType,
        onDismiss = {
            showBottomSheet.value = false
            currentSheetType = SheetType.Collections
        },
        onConfirm = {
            onAddCollection(it)
            currentSheetType = SheetType.Collections
        },
        onSheetTypeChange = {
            showBottomSheet.value = true
            currentSheetType = it
        },
        onCheck = { movieCollectionItem, isChecked ->
            if (movieCollectionItem.name == "Вам было интересно") {
                isFav = !isFav
                onFavouriteClicked(movie, !isChecked)
            } else if (movieCollectionItem.name == "Посмотрено") {
                isSeen = !isSeen
                onLikeClicked(movie, !isChecked)
            }
        },
        checked = checked,
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(40.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                item {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(600.dp)
                    ) {
                        FetchedImage(
                            linkToImage = movie.posterUrl,
                            modifierForParent = Modifier
                                .fillMaxWidth()
                                .matchParentSize(),
                        )
                        TitleSection(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            movie = movie,
                            isLicked = isSeen,
                            onLikeClicked = { movie, liked ->
                                onLikeClicked(movie, liked)
                                isSeen = !isSeen
                            },
                            onFavouriteClicked = {movie, favourite ->
                                onFavouriteClicked(movie,favourite)
                                isFav = !isFav
                            },
                            onShareClicked = onShareClicked,
                            onBlindEyeClicked = onBlindEyeClicked,
                            onMoreClicked = onMoreClicked,
                            isFavourite = isFav
                        )
                    }
                }
                item {
                    MainDescriptionWithSubDescription(
                        mainDescription = movie.description,
                        subDescription = movie.shortDescription,
                        modifier = Modifier
                            .padding(horizontal = 26.dp)
                    )
                }
                item {
                    ActorsSection(
                        title = "В фильме снимались",
                        countOther = actors.size,
                        itemInColumn = 2,
                        actors = actors,
                        onActorClick = onActorClick,
                        onTitleClick = {},
                        modifier = Modifier.padding(horizontal = 26.dp)
                    )
                }
                item {
                    ActorsSection(
                        title = "Над фильмом работали",
                        countOther = filmCrew.size,
                        itemInColumn = 2,
                        actors = filmCrew,
                        onActorClick = onActorClick,
                        onTitleClick = {},
                        modifier = Modifier.padding(horizontal = 26.dp)
                    )
                }
                item {
                    GallerySection(
                        count = galleries.size,
                        galleries = galleries,
                        onGalleryClick = { onGalleryClick(movie) },
                        modifier = Modifier.padding(horizontal = 26.dp)
                    )
                }
                item {
                    RelatedMoviesSection(
                        countOrAll = similarMovies.size.toString(),
                        relatedMovies = similarMovies,
                        onMovieClick = onMovieClick,
                        modifier = Modifier
                            .padding(horizontal = 26.dp)
                    )
                }
            }
        },
    )
}

@Composable
private fun TitleSection(
    isFavourite: Boolean,
    isLicked: Boolean,
    movie: Movie,
    onLikeClicked: (Movie, Boolean) -> Unit,
    onFavouriteClicked: (Movie, Boolean) -> Unit,
    onShareClicked: () -> Unit,
    onBlindEyeClicked: () -> Unit,
    onMoreClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "${movie.ratingKp} ${movie.name}",
            color = Color(0xFFB5B5C9),
            modifier = Modifier.padding(top = 40.dp)
        )
        Row {
            Text(text = "${movie.year}", color = Color(0xFFB5B5C9))
            movie.genres.forEachIndexed { index, genre ->
                if (index < movie.genres.size - 1) Text(text = ",")
                Text(text = genre.genre, color = Color(0xFFB5B5C9))
            }
        }
        Row {
            movie.countries.forEachIndexed { index, country ->
                if (index < movie.countries.size - 1) Text(text = ",")
                Text(text = country.country, color = Color(0xFFB5B5C9))
            }
            Text(text = "${movie.lengthMovie} ${movie.ageLimit}")
        }
        Row {
            IconButton(onClick = {
                onLikeClicked(movie, isLicked)
            }) {
                val color = if (isLicked) Color.Red else Color.White
                Icon(
                    painter = painterResource(id = R.drawable.icon_liked),
                    contentDescription = "",
                    tint = color
                )
            }
            IconButton(onClick = {
                onFavouriteClicked(movie, isFavourite)
            }) {
                val color = if (isFavourite) Color.Yellow else Color.White
                Icon(
                    painter = painterResource(id = R.drawable.icon_favourite),
                    contentDescription = "",
                    tint = color
                )
            }
            IconButton(onClick = onBlindEyeClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_blind_eye),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(onClick = onShareClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_share),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(onClick = onMoreClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_more),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun MainDescriptionWithSubDescription(
    mainDescription: String,
    subDescription: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = mainDescription,
            fontWeight = FontWeight.W700,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = subDescription,
            fontWeight = FontWeight.W400,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ActorsSection(
    onTitleClick: () -> Unit,
    onActorClick: (Actor) -> Unit,
    title: String,
    countOther: Int,
    itemInColumn: Int,
    actors: List<Actor>,
    modifier: Modifier = Modifier
) {
    Column {
        StepTitle(
            title = title,
            countOrOther = countOther.toString(),
            modifier = modifier,
            onTitleClick = onTitleClick
        )
        LazyHorizontalGrid(
            rows = GridCells.Fixed(itemInColumn),
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            items(actors) { actor ->
                ActorItem(
                    actor = actor,
                    onActorClick = onActorClick,
                    modifierForParent = Modifier.size(width = 207.dp, height = 68.dp)
                )
            }
        }
    }
}

@Composable
private fun GallerySection(
    onGalleryClick: () -> Unit,
    count: Int,
    galleries: List<GalleryImage>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        StepTitle(
            title = "Галерея",
            countOrOther = count.toString(),
            modifier = modifier,
            onTitleClick = onGalleryClick
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            galleries.forEach { galleryImage ->
                item {
                    GalleryCard(galleryImage = galleryImage)
                }
            }
        }
    }
}

@Composable
private fun GalleryCard(
    galleryImage: GalleryImage,
    modifier: Modifier = Modifier
) {
    FetchedImage(
        linkToImage = galleryImage.imageUrl,
        modifierForParent = modifier
            .width(192.dp)
            .height(108.dp)
            .clip(RoundedCornerShape(8.dp)),
        modifierForImage = Modifier.size(width = 192.dp, height = 108.dp)
    )
}


