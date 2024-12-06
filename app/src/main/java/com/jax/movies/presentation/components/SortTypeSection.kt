package com.jax.movies.presentation.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.R
import com.jax.movies.domain.entity.home.Country
import com.jax.movies.domain.entity.home.Genre

@Composable
fun <T> SortTypeSection(
    query: String,
    onSearchClick: (String) -> Unit,
    onNewQuery: (String) -> Unit,
    types: T,
    topBarTitle: String,
    searchBarTitle: String,
    onChooseType: (T) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                onNavClick = onClickBack,
                navIcon = R.drawable.icon_back,
                title = topBarTitle
            )
        },
        modifier = modifier
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            CustomSearchBar(
                currentQuery = query,
                leadingIcon = R.drawable.icon_search,
                placeHolderText = searchBarTitle,
                onSearchClick = onSearchClick,
                onNewQuery = onNewQuery,
                modifier = Modifier.padding(16.dp)
            )
            ChooseTypeSection(
                types = types,
                onTypeClick = {
                    Log.d("clicked", "ChooseTypeSection: $it")
                    onChooseType(it)
                }
            )
        }
    }
}

@Composable
private fun <T> ChooseTypeSection(
    onTypeClick: (T) -> Unit,
    types: T
) {
    Column {
        val text = when(types){
            is Genre-> types.name
            is Country -> types.name
            else -> ""
        }
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color(0xFF272727),
            modifier = Modifier
                .padding(16.dp)
                .height(50.dp)
                .fillMaxWidth()
                .clickable {
                    Log.d("clicked", "ChooseTypeSection: $types")
                    onTypeClick(types)
                }
        )
        HorizontalDivider()
    }
}