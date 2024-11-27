package com.jax.movies.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.jax.movies.data.remote.api.MoviesApiFactory
import com.jax.movies.data.remote.api.MoviesApiService
import com.jax.movies.navigation.root.RootNavGraph
import com.jax.movies.presentation.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
                Box(modifier = Modifier.fillMaxSize()) { RootNavGraph() }
            }
           /* lifecycleScope.launch {
                val responseDto = MoviesApiFactory.apiService.searchByQuery("s")
                if (responseDto.isSuccessful) {
                    Log.d("dsadasdsadsadasdsa", "onCreate: ${responseDto.body()?.films}")
                }
            }*/
        }
    }
}


