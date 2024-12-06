package com.jax.movies.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    currentQuery: String = "",
    leadingIcon: Int = R.drawable.icon_search,
    placeHolderText: String = stringResource(R.string.searchbarPlaceHolder),
    onSearchClick: (String) -> Unit,
    onNewQuery: (String) -> Unit,
    onTrailingIcon1Click: () -> Unit = {},
    onTrailingIcon2Click: () -> Unit = {},
    trailingIcon1: Int? = null,
    trailingIcon2: Int? = null,
    modifier: Modifier = Modifier
) {
    var searchValue by remember { mutableStateOf(TextFieldValue(currentQuery)) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(68.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        IconButton(
            onClick = { onSearchClick(searchValue.text) },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = painterResource(leadingIcon),
                contentDescription = "search"
            )
        }

        TextField(
            value = searchValue,
            onValueChange = { newValue ->
                searchValue = newValue.copy(
                    text = newValue.text,
                    selection = newValue.selection
                )
                onNewQuery(newValue.text)
            },
            placeholder = {
                Text(
                    text = placeHolderText,
                    color = Color(0xFF838390),
                    fontSize = 14.sp
                )
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                hintLocales = LocaleList(Locale("ru"))
            ),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .focusRequester(focusRequester)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            trailingIcon1?.let {
                IconButton(onClick = {
                    onTrailingIcon1Click()
                    searchValue = TextFieldValue("")
                }) {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = "trailing icon 1",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            trailingIcon2?.let {
                IconButton(onClick = onTrailingIcon2Click) {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = "trailing icon 2",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}





