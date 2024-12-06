package com.jax.movies.presentation.search.period

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.R
import com.jax.movies.presentation.components.MyTopAppBar

@Composable
fun PeriodScreen(
    onClickBack: () -> Unit,
    onChoosePeriod: (Int, Int) -> Unit
) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                onNavClick = onClickBack,
                navIcon = R.drawable.icon_back,
                title = "Период"
            )
        }
    ){innerPadding->
        YearRangePicker(
            onRangeSelected = onChoosePeriod,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun YearRangePicker(
    initialRange: IntRange = 1998..2009,
    onRangeSelected: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedStartYear by remember { mutableIntStateOf(initialRange.first) }
    var selectedEndYear by remember { mutableIntStateOf(initialRange.last) }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "Искать в период с",
            color = Color(0xFF838390),
            fontSize = 14.sp,
            modifier = Modifier.padding(16.dp)
        )
        SingleYearPicker(
            initialRange = initialRange,
            selectedYear = selectedStartYear,
            onYearSelected = { selectedStartYear = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Искать в период до",
            color = Color(0xFF838390),
            fontSize = 14.sp,
            modifier = Modifier.padding(16.dp)
        )
        SingleYearPicker(
            initialRange = initialRange,
            selectedYear = selectedEndYear,
            onYearSelected = { selectedEndYear = it }
        )
        Button(
            onClick = { onRangeSelected(selectedStartYear, selectedEndYear) },
            modifier = Modifier
                .padding(start = 150.dp)
                .padding(25.dp)
        ) {
            Text(text = "Выбрать")
        }
    }
}

@Composable
fun SingleYearPicker(
    initialRange: IntRange,
    selectedYear: Int,
    onYearSelected: (Int) -> Unit
) {
    var currentRange by remember { mutableStateOf(initialRange) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.border(
            border = BorderStroke(
                width = 3.dp,
                color = Color.Black
            ),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        TimeSwitchers(
            currentRange = currentRange,
            onRangeChanged = { newRange -> currentRange = newRange }
        )
        LazyYearPicker(
            range = currentRange,
            selectedYear = selectedYear,
            onYearSelected = onYearSelected
        )
    }
}

@Composable
fun TimeSwitchers(
    currentRange: IntRange,
    onRangeChanged: (IntRange) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "${currentRange.first} - ${currentRange.last}",
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            color = Color(0xFF3D3BFF),
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        )
        IconButton(onClick = {
            onRangeChanged((currentRange.first - 12)..(currentRange.last - 12))
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Previous",
            )
        }
        IconButton(onClick = {
            onRangeChanged((currentRange.first + 12)..(currentRange.last + 12))
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Next"
            )
        }
    }
}

@Composable
fun LazyYearPicker(
    range: IntRange,
    selectedYear: Int,
    onYearSelected: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(26.dp)
    ) {
        items(range.toList()) { year ->
            val isSelected = year == selectedYear
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .size(20.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) Color.Blue else Color.Transparent)
                    .clickable { onYearSelected(year) }
            ) {
                Text(
                    text = year.toString(),
                    color = if (isSelected) Color.White else Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
            }
        }
    }
}
