package com.jax.movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.jax.movies.data.remote.api.MoviesApiFactory
import com.jax.movies.presentation.main.MoviesMainScreen
import com.jax.movies.ui.theme.MoviesTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
              /*  MoviesMainScreen()*/
                lifecycleScope.launch {
                    val movies = MoviesApiFactory.apiService.getPremieres(2023, "JANUARY")
                    Log.d("dsadasdasdsa", "onCreate: $movies")
                }
            }
        }
    }
}


