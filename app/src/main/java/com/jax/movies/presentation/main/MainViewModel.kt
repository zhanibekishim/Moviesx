package com.jax.movies.presentation.main

import androidx.lifecycle.ViewModel
import com.jax.movies.domain.entity.Movie
import com.jax.movies.domain.usecase.GetPremiersListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel() : ViewModel() {
    private val getPremiersListUseCase = GetPremiersListUseCase()
    private val _state = MutableStateFlow<MoviesListScreenState>(MoviesListScreenState.Initial)
    val state: StateFlow<MoviesListScreenState> get() = _state.asStateFlow()

    suspend fun fetchMovies() {
        _state.value = MoviesListScreenState.Loading
        getPremiersListUseCase().collect { result ->
            result.fold(
                onFailure = { exception ->
                    _state.value = MoviesListScreenState.Error(exception.toString())
                },
                onSuccess = { movies ->
                    _state.value = MoviesListScreenState.Success(movies)
                }
            )
        }
    }
}

sealed class MoviesListScreenState {
    data object Initial : MoviesListScreenState()
    data object Loading : MoviesListScreenState()
    data class Error(val message: String) : MoviesListScreenState()
    data class Success(val movies: List<Movie>) : MoviesListScreenState()
}