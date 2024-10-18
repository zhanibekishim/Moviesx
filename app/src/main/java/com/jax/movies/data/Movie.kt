package com.jax.movies.data

import androidx.compose.ui.graphics.painter.Painter

data class Movie(
    val id: Long,
    val title: String,
    val genre: String,
    val image: Painter? = null,
    val rating: Double = 7.8
)

object AllMovies {
    val premieres = listOf(
        Movie(id = 1, title = "Premiere 1", genre = "Genre 1"),
        Movie(id = 2, title = "Premiere 2", genre = "Genre 2"),
        Movie(id = 3, title = "Premiere 3", genre = "Genre 3"),
        Movie(id = 4, title = "Premiere 4", genre = "Genre 4"),
        Movie(id = 5, title = "Premiere 5", genre = "Genre 5"),
        Movie(id = 6, title = "Premiere 6", genre = "Genre 6"),
        Movie(id = 7, title = "Premiere 7", genre = "Genre 7"),
        Movie(id = 8, title = "Premiere 8", genre = "Genre 8"),
        Movie(id = 9, title = "Premiere 9", genre = "Genre 9"),
        Movie(id = 10, title = "Premiere 10", genre = "Genre 10"),
        Movie(id = 11, title = "Premiere 11", genre = "Genre 11"),
        Movie(id = 12, title = "Premiere 12", genre = "Genre 12")
    )
    val popular = listOf(
        Movie(id = 1, title = "popular 1", genre = "Genre 1"),
        Movie(id = 2, title = "popular 2", genre = "Genre 2"),
        Movie(id = 3, title = "popular 3", genre = "Genre 3"),
        Movie(id = 4, title = "popular 4", genre = "Genre 4"),
        Movie(id = 5, title = "popular 5", genre = "Genre 5"),
        Movie(id = 6, title = "popular 6", genre = "Genre 6"),
        Movie(id = 7, title = "popular 7", genre = "Genre 7"),
        Movie(id = 8, title = "popular 8", genre = "Genre 8"),
        Movie(id = 9, title = "popular 9", genre = "Genre 9"),
        Movie(id = 10, title = "popular 10", genre = "Genre 10"),
        Movie(id = 11, title = "popular 11", genre = "Genre 11"),
        Movie(id = 12, title = "popular 12", genre = "Genre 12")
    )

    val militants = listOf(
        Movie(id = 1, title = "Militant 1", genre = "Genre 1"),
        Movie(id = 2, title = "Militant 2", genre = "Genre 2"),
        Movie(id = 3, title = "Militant 3", genre = "Genre 3"),
        Movie(id = 4, title = "Militant 4", genre = "Genre 4"),
        Movie(id = 5, title = "Militant 5", genre = "Genre 5"),
        Movie(id = 6, title = "Militant 6", genre = "Genre 6"),
        Movie(id = 7, title = "Militant 7", genre = "Genre 7"),
        Movie(id = 8, title = "Militant 8", genre = "Genre 8"),
        Movie(id = 9, title = "Militant 9", genre = "Genre 9"),
        Movie(id = 10, title = "Militant 10", genre = "Genre 10"),
        Movie(id = 11, title = "Militant 11", genre = "Genre 11"),
        Movie(id = 12, title = "Militant 12", genre = "Genre 12")
    )

    val top = listOf(
        Movie(id = 1, title = "Top 1", genre = "Genre 1"),
        Movie(id = 2, title = "Top 2", genre = "Genre 2"),
        Movie(id = 3, title = "Top 3", genre = "Genre 3"),
        Movie(id = 4, title = "Top 4", genre = "Genre 4"),
        Movie(id = 5, title = "Top 5", genre = "Genre 5"),
        Movie(id = 6, title = "Top 6", genre = "Genre 6"),
        Movie(id = 7, title = "Top 7", genre = "Genre 7"),
        Movie(id = 8, title = "Top 8", genre = "Genre 8"),
        Movie(id = 9, title = "Top 9", genre = "Genre 9"),
        Movie(id = 10, title = "Top 10", genre = "Genre 10"),
        Movie(id = 11, title = "Top 11", genre = "Genre 11"),
        Movie(id = 12, title = "Top 12", genre = "Genre 12")
    )

    val dramaOfFrance = listOf(
        Movie(id = 1, title = "France 1", genre = "Genre 1"),
        Movie(id = 2, title = "France 2", genre = "Genre 2"),
        Movie(id = 3, title = "France 3", genre = "Genre 3"),
        Movie(id = 4, title = "France 4", genre = "Genre 4"),
        Movie(id = 5, title = "France 5", genre = "Genre 5"),
        Movie(id = 6, title = "France 6", genre = "Genre 6"),
        Movie(id = 7, title = "France 7", genre = "Genre 7"),
        Movie(id = 8, title = "France 8", genre = "Genre 8"),
        Movie(id = 9, title = "France 9", genre = "Genre 9"),
        Movie(id = 10, title = "France 10", genre = "Genre 10"),
        Movie(id = 11, title = "France 11", genre = "Genre 11"),
        Movie(id = 12, title = "France 12", genre = "Genre 12")
    )

    val show = listOf(
        Movie(id = 1, title = "Show 1", genre = "Serial 1"),
        Movie(id = 2, title = "Show 2", genre = "Serial 2"),
        Movie(id = 3, title = "Show 3", genre = "Serial 3"),
        Movie(id = 4, title = "Show 4", genre = "Serial 4"),
        Movie(id = 5, title = "Show 5", genre = "Serial 5"),
        Movie(id = 6, title = "Show 6", genre = "Serial 6"),
        Movie(id = 7, title = "Show 7", genre = "Serial 7"),
        Movie(id = 8, title = "Show 8", genre = "Serial 8"),
        Movie(id = 9, title = "Show 9", genre = "Serial 9"),
        Movie(id = 10, title = "Show 10", genre = "Serial 10"),
        Movie(id = 11, title = "Show 11", genre = "Serial 11"),
        Movie(id = 12, title = "Show 12", genre = "Serial 12")
    )
}