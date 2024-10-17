package com.jax.movies.data

import androidx.compose.ui.graphics.painter.Painter

data class Movie (
    val id: Long,
    val title: String,
    val genre: String,
    val image: Painter? = null,
    val rating: Double = 7.8
)

object AllMovies {
    val premieres = listOf(
        Movie(id = 1,title = "Premiere 1", genre = "Genre 1"),
        Movie(id = 2,title = "Premiere 2", genre = "Genre 2"),
        Movie(id = 3,title = "Premiere 3", genre = "Genre 3"),
        Movie(id = 4,title = "Premiere 4", genre = "Genre 4")
    )
    val popular = listOf(
        Movie(id = 1,title = "Movie 1", genre = "Genre 1"),
        Movie(id = 2,title = "Movie 2", genre = "Genre 2"),
        Movie(id = 3,title = "Movie 3", genre = "Genre 3"),
        Movie(id = 4,title = "Movie 4", genre = "Genre 4")
    )
    val militants = listOf(
        Movie(id = 1,title = "Militant 1", genre = "Genre 1"),
        Movie(id = 2,title = "Militant 2", genre = "Genre 2"),
        Movie(id = 3,title = "Militant 3", genre = "Genre 3"),
        Movie(id = 4,title = "Militant 4", genre = "Genre 4")
    )
    val top = listOf(
        Movie(id = 1,title = "Top 1", genre = "Genre 1"),
        Movie(id = 2,title = "Top 2", genre = "Genre 2"),
        Movie(id = 3,title = "Top 3", genre = "Genre 3"),
        Movie(id = 4,title = "Top 4", genre = "Genre 4")
    )
    val dramaOfFrance = listOf(
        Movie(id = 1,title = "France 1", genre = "Drama"),
        Movie(id = 2,title = "France 2", genre = "Drama"),
        Movie(id = 3,title = "France 3", genre = "Drama"),
        Movie(id = 4,title = "France 4", genre = "Drama")
    )
    val show = listOf(
        Movie(id = 1,title = "Show 1", genre = "Serial"),
        Movie(id = 2,title = "Show 2", genre = "Serial"),
        Movie(id = 3,title = "Show 3", genre = "Serial"),
        Movie(id = 4,title = "Show 4", genre = "Serial")
    )
}