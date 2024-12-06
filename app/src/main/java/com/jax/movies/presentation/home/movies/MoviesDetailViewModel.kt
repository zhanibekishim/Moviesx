package com.jax.movies.presentation.home.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.entity.home.MoviesType
import com.jax.movies.domain.usecase.home.GetMovieCollectionUseCaseImpl
import com.jax.movies.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MoviesDetailViewModel @AssistedInject constructor(
    @Assisted private val moviesType: MoviesType,
    private val getMovieCollectionUseCase: GetMovieCollectionUseCaseImpl
) : ViewModel() {

    private val _state: MutableStateFlow<MoviesScreenState> =
        MutableStateFlow(MoviesScreenState.Initial)
    val state: StateFlow<MoviesScreenState> = _state.asStateFlow()

    private val _moviesNavigationChannel = Channel<MoviesScreenIntent>()
    val moviesNavigationChannel = _moviesNavigationChannel.receiveAsFlow()

    fun handleIntent(intent: MoviesScreenIntent) {
        when (intent) {
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
        when (action) {
            is MoviesScreenAction.FetchMoviesDetailInfo -> {
                fetchMoviesDetail(moviesType)
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
                        _state.update {
                            MoviesScreenState.Error(
                                result.message.localizedMessage ?: "Unknown error"
                            )
                        }
                    }

                    is Resource.Success -> {
                        Log.d("MoviesDetailViewModel", "Fetched movies: ${result.data}")
                        _state.update {
                            MoviesScreenState.Success(
                                movies = result.data,
                                type = type
                            )
                        }
                    }
                }
            }
        }
    }

    @AssistedFactory
    interface MoviesDetailFactory {
        fun create(moviesType: MoviesType): MoviesDetailViewModel
    }
    
    companion object{
        fun provideMoviesViewModel(
            moviesType: MoviesType,
            factory: MoviesDetailFactory
        ):ViewModelProvider.Factory = object:ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(moviesType) as T
            }
        }
    }
}












