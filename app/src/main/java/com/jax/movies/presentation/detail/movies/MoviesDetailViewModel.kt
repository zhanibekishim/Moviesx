package com.jax.movies.presentation.detail.movies

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.usecase.GetMovieCollectionUseCaseImpl
import com.jax.movies.presentation.movies.MoviesType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesDetailViewModel : ViewModel() {

    private val getMoviesCollectionUseCase = GetMovieCollectionUseCaseImpl()
    private val _state: MutableStateFlow<MoviesDetailState> =
        MutableStateFlow(MoviesDetailState.Initial)
    val state: StateFlow<MoviesDetailState> = _state.asStateFlow()

    fun fetchMoviesDetail(type:MoviesType){
        _state.value = MoviesDetailState.Loading
        viewModelScope.launch {
            getMoviesCollectionUseCase(type).collect { result ->
                result.fold(
                    onFailure = { exception ->
                        _state.value =
                            MoviesDetailState.Error(exception.message.toString())
                    },
                    onSuccess = { movies ->
                        _state.value = MoviesDetailState.Success(movies)
                    }
                )
            }
        }
    }
}