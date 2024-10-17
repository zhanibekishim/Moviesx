package com.jax.movies

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jax.movies.ui.theme.Blue1

sealed class  BottomItem(val iconID: Int, val route: String) {
    object Screen1: BottomItem(R.drawable.icon_home, "home")
    object Screen2: BottomItem(R.drawable.icon_search, "search")
    object Screen3: BottomItem(R.drawable.icon_profile, "profile")
}


@Composable
fun BottomNavigationBar(navController: NavController) {

    val listItems = listOf(
    BottomItem.Screen1,
    BottomItem.Screen2,
    BottomItem.Screen3
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        listItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconID),
                        contentDescription = null,
                        tint = if (currentRoute == item.route) Blue1 else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    }
}





