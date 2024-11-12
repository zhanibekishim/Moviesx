package com.jax.movies.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.utils.Resource
import com.jax.movies.domain.usecase.GetMovieCollectionUseCaseImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    private val getMovieCollectionUseCase = GetMovieCollectionUseCaseImpl()
    private val _homeScreenState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState())
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState.asStateFlow()

    init {
        MoviesType.entries.forEach { type ->
            fetchMovies(type)
        }
    }

    private fun fetchMovies(type: MoviesType) {
        updateMovieState(type, HomeScreenState.MoviesState.Loading)
        viewModelScope.launch {
            delay(3000)
            getMovieCollectionUseCase(type).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        updateMovieState(
                            type,
                            HomeScreenState.MoviesState.Error(result.message.cause.toString())
                        )
                    }
                    is Resource.Success -> {
                        updateMovieState(
                            type,
                            HomeScreenState.MoviesState.Success(movies = result.data)
                        )
                    }
                }
            }
        }
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
}
