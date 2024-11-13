package com.jax.movies.domain.entity

data class Actor(
    val actorId: Long,
    val nameRu: String,
    val nameEn: String,
    val description: String,
    val professionText: String,
    val professionKey: String,
)