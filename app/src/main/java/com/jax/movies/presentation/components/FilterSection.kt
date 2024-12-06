package com.jax.movies.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T> FilterSection(
    chosenType: T,
    onChoose: (T) -> Unit,
    types: List<T>,
    title: String,
    chosenContainerColor: Color = Color(0xFF3D3BFF),
    unChosenContainerColor: Color = Color.White,
    chosenTextColor: Color = Color.White,
    unChosenTextColor: Color = Color.Black,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = title,
            color = Color(0xFF838390),
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(0xFF272727)
                    ),
                    shape = RoundedCornerShape(56.dp)
                )
                .padding(4.dp)
        ) {
            types.forEach { type ->
                val bgColor =
                    if (chosenType == type) chosenContainerColor else unChosenContainerColor
                val textColor = if (chosenType == type) chosenTextColor else unChosenTextColor
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(bgColor)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { onChoose(type) }
                ) {
                    Text(
                        text = type.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        color = textColor
                    )
                }
            }
        }
    }
}