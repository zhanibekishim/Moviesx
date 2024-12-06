package com.jax.movies.presentation.components
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(text = "Закрыть")
                }

                Text(
                    text = "Ошибка!",
                    modifier = Modifier.align(Alignment.TopStart).padding(end = 80.dp)
                )
            }
        },
        text = {
            Text(
                text = "Во время обработки запроса произошла ошибка"
            )
        },
        confirmButton = {},
        shape = RoundedCornerShape(16.dp)
    )
}

