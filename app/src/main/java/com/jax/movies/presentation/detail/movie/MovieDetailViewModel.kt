package com.jax.movies.presentation.detail.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.entity.Movie
import com.jax.movies.domain.usecase.GetDetailMovieUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {
    private val getDetailMovieUseCaseImpl = GetDetailMovieUseCaseImpl()
    private val _state = MutableStateFlow<MovieDetailState>(MovieDetailState.Initial)
    val state: StateFlow<MovieDetailState> get() = _state.asStateFlow()

    fun fetchDetailInfo(movie: Movie) {
        _state.value = MovieDetailState.Loading
        viewModelScope.launch {
            getDetailMovieUseCaseImpl(movie.id).fold(
                onFailure = { message ->
                    _state.value = MovieDetailState.Error(message.toString())
                },
                onSuccess = { movie ->
                    _state.value = MovieDetailState.Success(movie)
                }
            )
        }
    }
}
