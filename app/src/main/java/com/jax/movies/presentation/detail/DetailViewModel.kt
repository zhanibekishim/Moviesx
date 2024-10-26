package com.jax.movies.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.entity.Movie
import com.jax.movies.domain.usecase.GetDetailMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel() : ViewModel() {
    private val getDetailMovieUseCase = GetDetailMovieUseCase()
    private val _state = MutableStateFlow<DetailScreenState>(DetailScreenState.Initial)
    val state: StateFlow<DetailScreenState> get() = _state.asStateFlow()

    fun fetchDetailInfo(id: Long) {
        _state.value = DetailScreenState.Loading
        viewModelScope.launch {
            getDetailMovieUseCase(id).fold(
                onFailure = { message ->
                    _state.value = DetailScreenState.Error(message.toString())
                },
                onSuccess = { movie ->
                    _state.value = DetailScreenState.Success(movie)
                }
            )
        }
    }
}

sealed class DetailScreenState {
    data object Initial : DetailScreenState()
    data object Loading : DetailScreenState()
    data class Error(val message: String) : DetailScreenState()
    data class Success(val movie: Movie) : DetailScreenState()
}