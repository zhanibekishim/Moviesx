package com.jax.movies.presentation.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jax.movies.R
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.theme.Blue1
import com.jax.movies.utils.Constants.MOVIE_COLLECTION

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenWithBottomSheet(
    topBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    movie: Movie,
    collection: List<MovieCollectionItem>,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    sheetType: SheetType,
    checked: List<MovieCollectionItem>,
    onCheck: (MovieCollectionItem, Boolean) -> Unit,
    onSheetTypeChange: (SheetType) -> Unit
) {
    Log.d("dasdasdasdsadsadasdsa", sheetType.toString())
    val sheetState = rememberBottomSheetScaffoldState()
    val collectionName = remember { mutableStateOf(TextFieldValue("")) }
    BottomSheetScaffold(
        topBar = topBar,
        scaffoldState = sheetState,
        sheetContent = {
            when (sheetType) {
                is SheetType.Collections -> {
                    CollectionSheet(
                        movie = movie,
                        checked = checked,
                        onNewCollection = { onSheetTypeChange(SheetType.NewCollection) },
                        onDismiss = onDismiss,
                        collection = collection,
                        onCheck = onCheck
                    )
                }

                is SheetType.NewCollection -> {
                    NewCollectionSheet(
                        collectionName = collectionName.value,
                        onNameChange = { collectionName.value = it },
                        onSubmit = {
                            val existCollections = collection.map { it.name }
                            if(existCollections.contains(it)){
                                onSheetTypeChange(SheetType.Error)
                            }else{
                                onConfirm(it)
                            }
                        },
                        onDismiss = onDismiss
                    )
                }

                is SheetType.Error -> {
                    ErrorSheet(
                        message = "Такая коллекция уже существует",
                        onDismiss = onDismiss
                    )
                }

                else -> {}
            }
        },
        sheetDragHandle = {
            BottomSheetDefaults.DragHandle()
        },
        sheetShape = MaterialTheme.shapes.medium,
        modifier = Modifier.clickable {
            onSheetTypeChange(SheetType.Collections)
        }
    ) { innerPadding ->
        content(innerPadding)
    }

    LaunchedEffect(sheetType) {
        Log.d("SheetTypeChange", sheetType.toString())
        if (sheetType != SheetType.None) {
            sheetState.bottomSheetState.expand()
        } else {
            try {
                sheetState.bottomSheetState.hide()
            } catch (e: IllegalStateException) {
                Log.e("BottomSheet", "Cannot collapse the bottom sheet", e)
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CollectionSheet(
    movie: Movie,
    checked: List<MovieCollectionItem>,
    onNewCollection: () -> Unit,
    onDismiss: () -> Unit,
    onCheck: (MovieCollectionItem, Boolean) -> Unit,
    collection: List<MovieCollectionItem>,
) {

    val checkedCollectionItems =
        remember { mutableStateListOf<MovieCollectionItem>().apply { addAll(checked) } }

    var folderOpened by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            MovieItem(movie = movie, vertically = false, onMovieClick = {})
            IconButton(onClick = onDismiss) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_delete),
                    contentDescription = null,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                folderOpened = !folderOpened
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_folder),
                    contentDescription = null
                )
            }
            Text(
                text = "Добавить в коллекцию",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (folderOpened) {
            collection.forEach { item ->
                CollectionItem(
                    checkValue = checkedCollectionItems.contains(item),
                    onCheck = { collectionItem, isChecked ->
                        if (isChecked) {
                            checkedCollectionItems.add(collectionItem)
                        } else {
                            checkedCollectionItems.remove(collectionItem)
                        }
                        onCheck(collectionItem, isChecked)
                    },
                    collectionItem = item
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                IconButton(onClick = onNewCollection) {
                    Icon(
                        painter = painterResource(id = R.drawable.icons_add),
                        contentDescription = null
                    )
                }
                Text(
                    text = "Создать свою коллекцию",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


@Composable
private fun CollectionItem(
    checkValue: Boolean,
    onCheck: (MovieCollectionItem, Boolean) -> Unit,
    collectionItem: MovieCollectionItem,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(if (checkValue) Color.Blue else Color.Transparent)
    ) {
        Checkbox(
            checked = checkValue,
            onCheckedChange = { isChecked ->
                onCheck(collectionItem, isChecked)
            }
        )
        Text(
            text = collectionItem.name,
            style = MaterialTheme.typography.titleMedium,
            color = if (checkValue) Color.White else Color.Black,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = collectionItem.count.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = if (checkValue) Color.White else Color.Black,
        )
    }
}

@Composable
fun NewCollectionSheet(
    collectionName: TextFieldValue,
    onNameChange: (TextFieldValue) -> Unit,
    onSubmit: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
        ) {
            TextField(
                value = collectionName,
                onValueChange = onNameChange,
                placeholder = {
                    Text(
                        text = "Придумайте название для новой коллекции",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .weight(1f)
            )
            IconButton(onClick = onDismiss) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_delete),
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSubmit(collectionName.text) },
            colors = ButtonColors(
                containerColor = Blue1,
                contentColor = Color.White,
                disabledContainerColor = Blue1,
                disabledContentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 100.dp)
        ) {
            Text("Готово")
        }
    }
}

@Preview
@Composable
fun ErrorSheet(
    message: String = "Во время обработки запроса произошла ошибка",
    onDismiss: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
        ) {
            Text("Ошибка!", style = MaterialTheme.typography.titleMedium)
            IconButton(onClick = onDismiss) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_delete),
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            message,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.width(300.dp)
        )
    }
}

@Entity(tableName = MOVIE_COLLECTION)
data class MovieCollectionItem(
    @PrimaryKey
    val name: String,
    val id: Int = 0,
    var count: Int,
)

sealed interface SheetType {
    data object None : SheetType
    data object Collections : SheetType
    data object NewCollection : SheetType
    data object Error : SheetType
}
