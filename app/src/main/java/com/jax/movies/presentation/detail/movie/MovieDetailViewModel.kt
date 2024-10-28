package com.jax.movies.presentation.detail.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.entity.Movie
import com.jax.movies.domain.usecase.GetDetailMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {
    private val getDetailMovieUseCase = GetDetailMovieUseCase()
    private val _state = MutableStateFlow<MovieDetailScreenState>(MovieDetailScreenState.Initial)
    val state: StateFlow<MovieDetailScreenState> get() = _state.asStateFlow()

    fun fetchDetailInfo(movie: Movie) {
        _state.value = MovieDetailScreenState.Loading
        viewModelScope.launch {
            getDetailMovieUseCase(movie.id).fold(
                onFailure = { message ->
                    _state.value = MovieDetailScreenState.Error(message.toString())
                },
                onSuccess = { movie ->
                    _state.value = MovieDetailScreenState.Success(movie)
                }
            )
        }
    }
}
