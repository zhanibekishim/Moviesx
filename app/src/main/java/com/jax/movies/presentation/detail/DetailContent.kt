package com.jax.movies.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.jax.movies.presentation.main.BottomScreenItem

@Composable
fun DetailContent(name: String){
    Text(text = name,
        fontSize = 34.sp)
}