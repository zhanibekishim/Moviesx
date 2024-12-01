package com.jax.movies.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.R
import com.jax.movies.utils.DefaultLists.yearsList

@Composable
fun PeriodSection(
    onPeriodChooseClick: (String,String) -> Unit,
    onClickBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                onNavClick = onClickBack,
                navIcon = R.drawable.icon_back,
                title = "Период"
            )
        }
    ) { padding ->
        var chosenYear by remember {
            mutableIntStateOf(1998)
        }
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            DatePicker(
                title = "Искать в период с",
                years = yearsList,
                onYearClick = {
                    chosenYear = it
                },
                onBackYearClick = {},
                onNextYearClick = {}
            )
            DatePicker(
                title = "Искать в период до",
                years = yearsList,
                onYearClick = {
                    chosenYear = it
                },
                onBackYearClick = {},
                onNextYearClick = {}
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        onPeriodChooseClick("chosenYear","chosenYear")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D3BFF)),
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Выбрать",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun DatePicker(
    title: String,
    years: List<String>,
    onBackYearClick: () -> Unit,
    onNextYearClick: () -> Unit,
    onYearClick: (Int) -> Unit,
    yearFrom: Int = 1998,
    yearTo: Int = 2009,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(16.dp)) {
        Text(
            text = title,
            color = Color(0xFF838390),
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Box(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(0xFF272727)
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = "$yearFrom-$yearTo",
                        color = Color(0xFF3D3BFF),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500
                    )
                    IconMix(
                        firstIconClick = onBackYearClick,
                        secondIconClick = onNextYearClick
                    )
                }
                YearsGrid(
                    years = years,
                    onYearClick = onYearClick,
                    modifier = Modifier.padding(horizontal = 40.dp, vertical = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun IconMix(
    firstIconClick: () -> Unit,
    secondIconClick: () -> Unit,
    firstIcon: Int = R.drawable.icon_before_year,
    secondIcon: Int = R.drawable.icon_next_year,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        IconButton(onClick = firstIconClick) {
            Icon(
                painter = painterResource(id = firstIcon),
                contentDescription = "before year",
                modifier = Modifier.size(15.dp)
            )
        }
        IconButton(onClick = secondIconClick) {
            Icon(
                painter = painterResource(id = secondIcon),
                contentDescription = "before year",
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

@Composable
private fun YearsGrid(
    onYearClick: (Int) -> Unit,
    years: List<String>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.padding(start = 12.dp)
    ) {
        years.forEach {
            item {
                Text(
                    text = it,
                    color = Color(0xFF272727),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onYearClick(it.toInt()) }
                )
            }
        }
    }
}
