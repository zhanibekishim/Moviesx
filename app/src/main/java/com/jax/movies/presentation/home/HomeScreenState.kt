package com.jax.movies.presentation.home

import com.jax.movies.domain.entity.Movie


/*
data class HomeScreenState(
    val moviesList: List<MoviesState>
) {
    sealed interface MoviesState {
        data object Initial : MoviesState
        data object Loading : MoviesState
        data class Error(val message: String) : MoviesState
        data class Success(val movies: List<Movie>, val moviesType: MoviesType) : MoviesState
    }
}
*/
data class HomeScreenState(
    val top250MoviesState: MoviesState = MoviesState.Initial,
    val popularMoviesState: MoviesState = MoviesState.Initial,
    val comicsMoviesState: MoviesState = MoviesState.Initial,
    val premiersMoviesState: MoviesState = MoviesState.Initial
) {
    sealed interface MoviesState {
        data object Initial : MoviesState
        data object Loading : MoviesState
        data class Error(val message: String) : MoviesState
        data class Success(val movies: List<Movie>) : MoviesState
    }
}


/*
data class HomeScreenState(
    val isLoading: Boolean = false,
    val top250Movies: List<Movie> = emptyList(),
    val topPopularMovies: List<Movie> = emptyList(),
    val comicsThemeMovies: List<Movie> = emptyList(),
    val premiersThemeMovies: List<Movie> = emptyList(),
    val errorMessage: String? = null
)
*/
