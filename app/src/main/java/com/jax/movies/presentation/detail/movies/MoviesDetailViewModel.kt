package com.jax.movies.presentation.detail.movies

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
    private val _state: MutableStateFlow<MoviesDetailScreenState> =
        MutableStateFlow(MoviesDetailScreenState.Initial)
    val state: StateFlow<MoviesDetailScreenState> = _state.asStateFlow()

    fun fetchMoviesDetail(type:MoviesType){
        _state.value = MoviesDetailScreenState.Loading
        viewModelScope.launch {
            getMoviesCollectionUseCase(type.name).collect { result ->
                result.fold(
                    onFailure = { exception ->
                        _state.value =
                            MoviesDetailScreenState.Error(exception.message.toString())
                    },
                    onSuccess = { movies ->
                        _state.value = MoviesDetailScreenState.Success(movies)
                    }
                )
            }
        }
    }
}