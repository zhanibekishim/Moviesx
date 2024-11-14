package com.jax.movies.domain.entity.films

data class Film(
    val filmId: Long,
    val nameRu: String,
    val nameEn: String,
    val rating: Double?,
    val description: String,
    val professionKey: String,
)