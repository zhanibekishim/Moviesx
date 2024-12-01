package com.jax.movies.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.R

@Composable
fun SortTypeSection(
    types: List<String>,
    topBarTitle: String,
    searchBarTitle: String,
    onChooseType: (String) -> Unit,
    onClickBack: () -> Unit,
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
            SearchBar(
                leadingIcon = R.drawable.icon_search,
                placeHolderText = searchBarTitle,
                onSearchClick = {},
                modifier = Modifier.padding(16.dp)
            )
            ChooseTypeSection(
                types = types,
                onTypeClick = onChooseType
            )
        }
    }
}

@Composable
private fun ChooseTypeSection(
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