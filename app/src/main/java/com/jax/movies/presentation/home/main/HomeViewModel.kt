package com.jax.movies.presentation.home.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.entity.home.MoviesType
import com.jax.movies.domain.usecase.home.GetMovieCollectionUseCaseImpl
import com.jax.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieCollectionUseCase: GetMovieCollectionUseCaseImpl
) : ViewModel() {

    private val _homeScreenState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState())
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState.asStateFlow()

    private val _homeNavigationChannel = Channel<HomeScreenIntent>(capacity = Channel.CONFLATED)
    val homeNavigationChannel = _homeNavigationChannel.receiveAsFlow()

    init {
        fetchAllMovies()
    }

    private fun fetchAllMovies() {
        viewModelScope.launch {
            val top250MoviesDeferred = async { fetchMovies(MoviesType.TOP_250_MOVIES) }
            val popularMoviesDeferred = async { fetchMovies(MoviesType.TOP_POPULAR_MOVIES) }
            val comicsMoviesDeferred = async { fetchMovies(MoviesType.COMICS_THEME) }
            val premiersMoviesDeferred = async { fetchMovies(MoviesType.PREMIERS) }

            val top250MoviesState = top250MoviesDeferred.await()
            val popularMoviesState = popularMoviesDeferred.await()
            val comicsMoviesState = comicsMoviesDeferred.await()
            val premiersMoviesState = premiersMoviesDeferred.await()

            _homeScreenState.update { currentState ->
                currentState.copy(
                    top250MoviesState = top250MoviesState,
                    popularMoviesState = popularMoviesState,
                    comicsMoviesState = comicsMoviesState,
                    premiersMoviesState = premiersMoviesState
                )
            }
        }
    }

    private fun fetchMovies(type: MoviesType): HomeScreenState.MoviesState {
        viewModelScope.launch {
            updateMovieState(type, HomeScreenState.MoviesState.Loading)
            delay(3000)

            getMovieCollectionUseCase(type).collect { result ->
                val newState = when (result) {
                    is Resource.Error -> HomeScreenState.MoviesState.Error(result.message.cause.toString())
                    is Resource.Success -> HomeScreenState.MoviesState.Success(movies = result.data)
                }
                updateMovieState(type, newState)
            }
        }

        return HomeScreenState.MoviesState.Loading
    }

    private fun updateMovieState(type: MoviesType, newState: HomeScreenState.MoviesState) {

        _homeScreenState.update { currentState ->
            when (type) {
                MoviesType.TOP_250_MOVIES -> currentState.copy(top250MoviesState = newState)
                MoviesType.TOP_POPULAR_MOVIES -> currentState.copy(popularMoviesState = newState)
                MoviesType.COMICS_THEME -> currentState.copy(comicsMoviesState = newState)
                MoviesType.PREMIERS -> currentState.copy(premiersMoviesState = newState)
            }
        }
    }

    fun handleIntent(intent: HomeScreenIntent) {
        when (intent) {
            is HomeScreenIntent.OnMovieClick -> {
                viewModelScope.launch {
                    _homeNavigationChannel.send(HomeScreenIntent.OnMovieClick(intent.movie))
                }
            }
            is HomeScreenIntent.OnMovieTypeClick -> {
                viewModelScope.launch {
                    _homeNavigationChannel.send(HomeScreenIntent.OnMovieTypeClick(intent.movieType))
                }
            }
            is HomeScreenIntent.OnProfileScreenClick -> {
                viewModelScope.launch {
                    _homeNavigationChannel.send(HomeScreenIntent.OnProfileScreenClick)
                }
            }
            is HomeScreenIntent.OnSearchScreenClick -> {
                viewModelScope.launch {
                    _homeNavigationChannel.send(HomeScreenIntent.OnSearchScreenClick)
                }
            }
            HomeScreenIntent.Default -> {}
        }
    }
}
