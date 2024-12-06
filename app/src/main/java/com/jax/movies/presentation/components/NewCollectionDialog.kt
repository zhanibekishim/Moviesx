package com.jax.movies.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jax.movies.R
import com.jax.movies.presentation.profile.CollectionItem

@Composable
fun NewCollectionDialog(
    collection: List<CollectionItem>,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val collectionNames = collection.map { it.title }
    var currentCollectionName by remember {
        mutableStateOf("")
    }
    var errorDialog by remember {
        mutableStateOf(false)
    }
    AlertDialog(
        title = {
            Row {
                TextField(
                    value = currentCollectionName,
                    onValueChange = {
                        currentCollectionName = it
                    },
                    placeholder = {
                        Text(text = "Придумайте название для вашей новой коллекции")
                    },
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_delete),
                        contentDescription = null
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if(collectionNames.contains(currentCollectionName)){
                        errorDialog = true
                    }else{
                        onDismiss()
                        onConfirm(currentCollectionName)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(
                    top = 100.dp,
                    end = 16.dp,
                    start = 16.dp,
                    bottom = 16.dp
                )
            ) {
                Text(text = "Готово")
            }
        },
        onDismissRequest = {}
    )
    if(errorDialog){
        ErrorDialog {
            errorDialog = false
        }
    }
}