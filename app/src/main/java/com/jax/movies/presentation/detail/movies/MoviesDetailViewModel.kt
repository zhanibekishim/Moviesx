package com.jax.movies.presentation.detail.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.usecase.GetMovieCollectionUseCaseImpl
import com.jax.movies.presentation.home.MoviesType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesDetailViewModel : ViewModel() {

    private val getMovieCollectionUseCase = GetMovieCollectionUseCaseImpl()
    private val _state: MutableStateFlow<MoviesDetailState> =
        MutableStateFlow(MoviesDetailState.Initial)
    val state: StateFlow<MoviesDetailState> = _state.asStateFlow()

    fun fetchMoviesDetail(type: MoviesType) {
        Log.d("dsadasdasasdas", "MainContent Screen$type")
        _state.value = MoviesDetailState.Loading
        viewModelScope.launch {
            getMovieCollectionUseCase(type).collect { result ->
                result.fold(
                    onFailure = { exception ->
                        _state.value =
                            MoviesDetailState.Error(exception.message.toString())
                    },
                    onSuccess = { movies ->
                        Log.d("dsadasdasasdas", "MainContent Screen$movies")
                        _state.value = MoviesDetailState.Success(movies = movies, type =  type)
                    }
                )
            }
        }
    }
}