package com.jax.movies.presentation.detail.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.entity.home.MoviesType
import com.jax.movies.domain.usecase.GetMovieCollectionUseCaseImpl
import com.jax.movies.utils.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {

    private val getMovieCollectionUseCase = GetMovieCollectionUseCaseImpl()
    private val _state: MutableStateFlow<MoviesScreenState> =
        MutableStateFlow(MoviesScreenState.Initial)
    val state: StateFlow<MoviesScreenState> = _state.asStateFlow()

    private val _moviesNavigationChannel = Channel<MoviesScreenIntent>()
    val moviesNavigationChannel = _moviesNavigationChannel.receiveAsFlow()

    fun handleIntent(intent: MoviesScreenIntent){
        when(intent){
            is MoviesScreenIntent.Default -> {}
            is MoviesScreenIntent.OnClickBack -> {
                viewModelScope.launch {
                    _moviesNavigationChannel.send(MoviesScreenIntent.OnClickBack)
                }
            }
            is MoviesScreenIntent.OnMovieClick -> {
                viewModelScope.launch {
                    _moviesNavigationChannel.send(MoviesScreenIntent.OnMovieClick(intent.movie))
                }
            }
        }
    }
    fun handleAction(action: MoviesScreenAction) {
        when(action){
            is MoviesScreenAction.FetchMoviesDetailInfo -> {
                fetchMoviesDetail(action.movieType)
            }
        }
    }
    private fun fetchMoviesDetail(type: MoviesType) {
        Log.d("MoviesDetailViewModel", "Fetching movies for type: $type")
        _state.value = MoviesScreenState.Loading
        viewModelScope.launch {
            getMovieCollectionUseCase(type).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update { MoviesScreenState.Error(result.message.localizedMessage ?: "Unknown error") }
                    }
                    is Resource.Success -> {
                        Log.d("MoviesDetailViewModel", "Fetched movies: ${result.data}")
                        _state.update { MoviesScreenState.Success(movies = result.data, type = type) }
                    }
                }
            }
        }
    }
}
