package com.jax.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.jax.movies.ui.theme.MoviesTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesTheme {
                //HomePage()
                Surface(color = MaterialTheme.colorScheme.background) {
                    OnBoardingScreen(onFinish = {

                        // Действие, когда пользователь завершает экран onboarding
                        // Например, переход к главному экрану приложения
                    })
                }
            }
        }
    }
}
