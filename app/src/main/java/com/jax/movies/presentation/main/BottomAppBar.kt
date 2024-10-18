package com.jax.movies.presentation.main

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jax.movies.ui.theme.Blue1


@Composable
fun BottomNavigationBar(
    navController: NavHostController,
   onSearchClick: () -> Unit,
    onProfileClick: () -> Unit,
    onHomeClick: () -> Unit
) {

    val listItems2 = listOf(
        BottomScreenItem.HomeScreen,
        BottomScreenItem.SearchScreen,
        BottomScreenItem.ProfileScreen,
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        listItems2.forEach { item ->
            AddItem(
                item = item,
                currentRoute = currentRoute.toString(),
                onClick = {
                    when (item) {
                        is BottomScreenItem.HomeScreen -> {
                            onHomeClick()
                        }
                        is BottomScreenItem.SearchScreen -> {
                            onSearchClick()
                        }
                        is BottomScreenItem.ProfileScreen -> {
                            onProfileClick()
                        }
                    }
                }
            )
        }

    }
}

@Composable
private fun RowScope.AddItem(
    item: BottomScreenItem,
    currentRoute: String,
    onClick: (String) -> Unit
) {
    NavigationBarItem(
        selected = currentRoute == item.route,
        onClick = { onClick(item.route) },
        icon = {
            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = null,
                tint = if (currentRoute == item.route) Blue1 else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        },
        label = {
            Text(text = item.title)
        }
    )
}

