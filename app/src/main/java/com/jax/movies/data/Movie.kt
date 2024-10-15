package com.jax.movies.data

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.ui.graphics.painter.Painter

data class Movie (
    val id: Long = 0L,
    val title: String,
    val genre: String,
    val image: Painter? = null,
    val rating: Double = 7.8
)
object allMovies {
    val premieres = listOf(
        Movie(title = "Premiere 1", genre = "Genre 1"),
        Movie(title = "Premiere 2", genre = "Genre 2"),
        Movie(title = "Premiere 3", genre = "Genre 3"),
        Movie(title = "Premiere 4", genre = "Genre 4")
    )
    val popular = listOf(
        Movie(title = "Movie 1", genre = "Genre 1"),
        Movie(title = "Movie 2", genre = "Genre 2"),
        Movie(title = "Movie 3", genre = "Genre 3"),
        Movie(title = "Movie 4", genre = "Genre 4")
    )
    val militants = listOf(
        Movie(title = "Militant 1", genre = "Genre 1"),
        Movie(title = "Militant 2", genre = "Genre 2"),
        Movie(title = "Militant 3", genre = "Genre 3"),
        Movie(title = "Militant 4", genre = "Genre 4")
    )
    val top = listOf(
        Movie(title = "Top 1", genre = "Genre 1"),
        Movie(title = "Top 2", genre = "Genre 2"),
        Movie(title = "Top 3", genre = "Genre 3"),
        Movie(title = "Top 4", genre = "Genre 4")
    )
    val dramaOfFrance = listOf(
        Movie(title = "France 1", genre = "Drama"),
        Movie(title = "France 2", genre = "Drama"),
        Movie(title = "France 3", genre = "Drama"),
        Movie(title = "France 4", genre = "Drama")
    )
    val show = listOf(
        Movie(title = "Show 1", genre = "Serial"),
        Movie(title = "Show 2", genre = "Serial"),
        Movie(title = "Show 3", genre = "Serial"),
        Movie(title = "Show 4", genre = "Serial")
    )
}