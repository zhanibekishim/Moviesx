package com.jax.movies.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jax.movies.R
import com.jax.movies.domain.entity.home.Movie

@Composable
fun AddCollectionDialog(
    movie: Movie,
    collections: List<MovieCollectionItem>,
    onNewCollectionAdd: () -> Unit,
    onItemAdd: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_delete),
                        contentDescription = null
                    )
                }
                MovieItem(
                    movie = movie,
                    onMovieClick = {},
                    modifier = Modifier.padding(26.dp)
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                HorizontalDivider()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_folder),
                        contentDescription = null
                    )
                    Text(
                        text = "Добавить в коллекцию",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(collections.size) { index ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            if (index < collections.size - 1) {
                                Checkbox(
                                    checked = false,
                                    onCheckedChange = { onItemAdd(collections[index].name) }
                                )
                            } else {
                                IconButton(onClick = onNewCollectionAdd) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.icon_add),
                                        contentDescription = null
                                    )
                                }
                            }
                            Text(text = collections[index].name, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        },
        confirmButton = {}
    )
}



