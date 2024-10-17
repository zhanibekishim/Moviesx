package com.jax.movies

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) } // Здесь добавляем BottomNavigationBar
    ) {
        NavGraph(navHostController = navController)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "onboarding") {
        composable("onboarding") {
            OnBoardingScreen(onFinish = {
                // Переход к главному экрану после завершения OnBoarding
                navHostController.navigate("home") {
                    // Удаляем все предыдущие экраны из стека, чтобы вернуться к ним нельзя было
                    popUpTo("onboarding") { inclusive = true }
                }
            })
        }
        composable("home") { HomePage() }
        composable("search") { SearchScreen() }
        composable("profile") { ProfileScreen() }



    }
}


@Composable
fun SearchScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Search Screen")
    }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Profile Screen")
    }
}
