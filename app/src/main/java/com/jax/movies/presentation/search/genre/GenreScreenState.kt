package com.jax.movies.presentation.search.genre

import com.jax.movies.domain.entity.home.Genre

sealed interface GenreScreenState {
    data object Initial : GenreScreenState
    data class Loading(val query: String) : GenreScreenState
    data class Error(val error: String, val query: String) : GenreScreenState
    data class Success(
        val genres: Genre = defaultGenre,
        val selectedGenre: Genre = defaultGenre,
        val query: String = "",
    ) : GenreScreenState

    companion object {
        val defaultGenres = listOf(
            Genre(name = "Комедия", id = 1),
            Genre(name = "Мелодрама", id = 2),
            Genre(name = "Боевик", id = 3),
            Genre(name = "Вестерн", id = 4),
            Genre(name = "Драма", id = 5)
        )
        val defaultGenre = Genre(name = "Комедия", id = 1)
    }
}