package com.jax.movies.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jax.movies.navigation.MainNavGraph
import com.jax.movies.navigation.RootNavGraph
import com.jax.movies.navigation.rememberNavigationState


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoviesMainScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        RootNavGraph()
    }
}


@Composable
fun MainPages() {
    val navigationState = rememberNavigationState()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navigationState.navHostController,
                onHomeClick = {
                    navigationState.navigateTo(BottomScreenItem.HomeScreen.route)
                },
                onProfileClick = {
                    navigationState.navigateTo(BottomScreenItem.ProfileScreen.route)
                },
                onSearchClick = {
                    navigationState.navigateTo(BottomScreenItem.SearchScreen.route)
                }
            )
        }
    ) { paddingValues ->
        MainNavGraph(
            navigationState = navigationState,
            paddingValues = paddingValues
        )
    }
}