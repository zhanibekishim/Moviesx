package com.jax.movies.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.entity.Movie
import com.jax.movies.domain.usecase.GetMoviesCollectionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    init {
        fetchMovies(listOf())
    }

    private val getMoviesCollectionsUseCase = GetMoviesCollectionsUseCase()
    private val _state: MutableStateFlow<MoviesListScreenState> = MutableStateFlow(MoviesListScreenState.Initial)
    val state: StateFlow<MoviesListScreenState> = _state.asStateFlow()

    private fun fetchMovies(types:List<String>) {
        _state.value = MoviesListScreenState.Loading
        viewModelScope.launch {
            getMoviesCollectionsUseCase(types).collect { result ->
                result.fold(
                    onFailure = { exception ->
                        _state.value = MoviesListScreenState.Error(exception.message.toString())
                    },
                    onSuccess = { movies ->
                        _state.value = MoviesListScreenState.Success(movies)
                    }
                )
            }
        }
    }
}

sealed class MoviesListScreenState {
    data object Initial : MoviesListScreenState()
    data object Loading : MoviesListScreenState()
    data class Error(val message: String) : MoviesListScreenState()
    data class Success(val movies: List<List<Movie>>) : MoviesListScreenState()
}