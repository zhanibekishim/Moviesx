package com.jax.movies.domain.entity

import com.jax.movies.data.remote.model.CountryNameContainerDto
import com.jax.movies.data.remote.model.GenreNameContainerDto

data class Movie(
    val id: Long,
    val name: String,
    val year: Int,
    val posterUrl: String,
    val slogan:String,
    val shortDescription: String,
    val description: String,
    val lengthMovie:String,
    val ageLimit:String,
    val genres: List<GenreNameContainerDto>,
    val countries: List<CountryNameContainerDto>,
)



