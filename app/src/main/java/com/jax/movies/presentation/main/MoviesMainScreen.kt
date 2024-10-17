package com.jax.movies.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jax.movies.navigation.RootNavGraph


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoviesMainScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        RootNavGraph()
    }
}
