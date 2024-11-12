package com.jax.movies.presentation.detail.films

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.utils.Resource
import com.jax.movies.domain.usecase.GetMovieCollectionUseCaseImpl
import com.jax.movies.presentation.home.MoviesType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FilmsViewModel : ViewModel() {

    private val getMovieCollectionUseCase = GetMovieCollectionUseCaseImpl()
    private val _state: MutableStateFlow<FilmsScreenState> =
        MutableStateFlow(FilmsScreenState.Initial)
    val state: StateFlow<FilmsScreenState> = _state.asStateFlow()

    fun fetchMoviesDetail(type: MoviesType) {
        Log.d("MoviesDetailViewModel", "Fetching movies for type: $type")
        _state.value = FilmsScreenState.Loading
        viewModelScope.launch {
            getMovieCollectionUseCase(type).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update { FilmsScreenState.Error(result.message.localizedMessage ?: "Unknown error") }
                    }
                    is Resource.Success -> {
                        Log.d("MoviesDetailViewModel", "Fetched movies: ${result.data}")
                        _state.update { FilmsScreenState.Success(movies = result.data, type = type) }
                    }
                }
            }
        }
    }
}
