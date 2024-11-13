package com.jax.movies.domain.entity

data class SimilarMovie(
    val id: Long,
    val nameRu: String,
    val nameEn: String,
    val posterUrl: String,
)
