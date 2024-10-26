package com.jax.movies.domain.entity

import com.jax.movies.data.remote.model.CountryNameContainerDto
import com.jax.movies.data.remote.model.GenreNameContainerDto

data class Movie(
    val id: Long,
    val name: String,
    val year: Int,
    val genres: List<GenreNameContainerDto>,
    val countries: List<CountryNameContainerDto>,
    val posterUrl: String,
)