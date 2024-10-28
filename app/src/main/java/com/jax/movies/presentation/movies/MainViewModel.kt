package com.jax.movies.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.usecase.GetMovieCollectionUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    init {
        MoviesType.entries.forEach {
            fetchMovies(it)
        }
    }

    private val getMoviesCollectionUseCase = GetMovieCollectionUseCaseImpl()

    private val _top250State: MutableStateFlow<MoviesScreenState> =
        MutableStateFlow(MoviesScreenState.Initial)
    val top250State: StateFlow<MoviesScreenState> = _top250State.asStateFlow()

    private val _topPopularsState: MutableStateFlow<MoviesScreenState> =
        MutableStateFlow(MoviesScreenState.Initial)
    val topPopularsState: StateFlow<MoviesScreenState> = _topPopularsState.asStateFlow()

    private val _premiersState: MutableStateFlow<MoviesScreenState> =
        MutableStateFlow(MoviesScreenState.Initial)
    val premiersState: StateFlow<MoviesScreenState> = _premiersState.asStateFlow()

    private val _boevikiState: MutableStateFlow<MoviesScreenState> =
        MutableStateFlow(MoviesScreenState.Initial)
    val boevikiState: StateFlow<MoviesScreenState> = _boevikiState.asStateFlow()

    private val _serialsState: MutableStateFlow<MoviesScreenState> =
        MutableStateFlow(MoviesScreenState.Initial)
    val serialsState: StateFlow<MoviesScreenState> = _serialsState.asStateFlow()

    private fun fetchMovies(type: MoviesType) {
        val currentState = when (type) {
            MoviesType.TOP_250 -> _top250State
            MoviesType.TOP_POPULARS -> _topPopularsState
            MoviesType.PREMIERS -> _premiersState
            MoviesType.BOEVIKI -> _top250State
            MoviesType.SERIALS -> _boevikiState
        }
        currentState.value = MoviesScreenState.Loading
        viewModelScope.launch {
            getMoviesCollectionUseCase(type.name).collect { result ->
                result.fold(
                    onFailure = { exception ->
                        currentState.value =
                            MoviesScreenState.Error(exception.message.toString())
                    },
                    onSuccess = { movies ->
                        currentState.value = MoviesScreenState.Success(movies)
                    }
                )
            }
        }
    }

}
