package com.jax.movies.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jax.movies.navigation.main.BottomScreenItem
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.presentation.components.BottomNavigationBar

@Composable
fun ProfileScreen(
    navigationState: NavigationState
) {
    val currentRoute = navigationState.navHostController.currentBackStackEntry?.destination?.route.toString()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute ,
                onHomeClick = {
                    navigationState.navigateTo(BottomScreenItem.HomeScreen.route)
                },
                onProfileClick = {
                    navigationState.navigateTo(BottomScreenItem.ProfileScreen.route)
                },
                onSearchClick = {
                    navigationState.navigateTo(BottomScreenItem.SearchScreen.route)
                },
            )
        }
    ){ innerPadding->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
            Text(text = "Profile Screen")
        }
    }
}
