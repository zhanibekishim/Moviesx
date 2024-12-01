package com.jax.movies.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jax.movies.R

@Composable
fun StepTitle(
    onTitleClick: () -> Unit,
    title: String,
    subTitle: String? = null,
    countOrOther: String,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = countOrOther,
                fontWeight = FontWeight.W600,
                fontSize = 14.sp,
                color = Color(0xFF3D3BFF)

            )
            IconButton(onClick = onTitleClick) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = "see all",
                    tint = Color(0xFF3D3BFF)
                )
            }
        }
        if (subTitle?.isNotEmpty() == true) {
            Text(
                text = subTitle,
                fontWeight = FontWeight.W400,
                fontSize = 12.sp,
                color = Color(0xFFB5B5C9),
                modifier = Modifier.weight(1f)
            )
        }
    }
}