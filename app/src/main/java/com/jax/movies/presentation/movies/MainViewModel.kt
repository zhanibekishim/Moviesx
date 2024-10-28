package com.jax.movies.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.usecase.GetMovieCollectionUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val getMovieCollectionUseCase = GetMovieCollectionUseCaseImpl()
    private val _moviesListState: MutableStateFlow<MoviesListState> =
        MutableStateFlow(MoviesListState(
            moviesList = MoviesType.entries.map { MoviesListState.MoviesState.Initial }
        ))
    val moviesListState: StateFlow<MoviesListState> = _moviesListState.asStateFlow()

    init {
        MoviesType.entries.forEach { type ->
            fetchMovies(type)
        }
    }

    private fun fetchMovies(type: MoviesType) {
        updateMovieState(type, MoviesListState.MoviesState.Loading)
        viewModelScope.launch {
            getMovieCollectionUseCase(type).collect { result ->
                result.fold(
                    onFailure = { exception ->
                        updateMovieState(
                            type,
                            MoviesListState.MoviesState.Error(exception.message.toString())
                        )
                    },
                    onSuccess = { movies ->
                        updateMovieState(
                            type,
                            MoviesListState.MoviesState.Success(movies = movies, moviesType =type)
                        )
                    }
                )
            }
        }
    }

    private fun updateMovieState(type: MoviesType, newState: MoviesListState.MoviesState) {
        _moviesListState.update { currentState ->
            val updatedMoviesList = currentState.moviesList.toMutableList()
            val index = MoviesType.entries.indexOf(type)
            updatedMoviesList[index] = newState
            currentState.copy(moviesList = updatedMoviesList)
        }
    }
}



























