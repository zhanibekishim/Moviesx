package com.jax.movies.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    leadingIcon: Int = R.drawable.icon_search,
    placeHolderText: String = stringResource(R.string.searchbarPlaceHolder),
    onSearchClick: (String) -> Unit,
    onTrailingIcon1Click: () -> Unit = {},
    onTrailingIcon2Click: () -> Unit = {},
    trailingIcon1: Int? = null,
    trailingIcon2: Int? = null,
    modifier: Modifier = Modifier
) {
    var searchValue by remember {
        mutableStateOf("")
    }
    androidx.compose.material3.SearchBar(
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
                IconButton(onClick = onTrailingIcon1Click) {
                    trailingIcon1?.let { painterResource(id = it) }?.let {
                        Icon(
                            painter = it,
                            contentDescription = "sort",
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
                IconButton(onClick = onTrailingIcon2Click) {
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