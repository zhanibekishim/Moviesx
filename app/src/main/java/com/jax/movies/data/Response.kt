package com.jax.movies.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    @SerialName("total")
    val total: Int,
    @SerialName("totalPages")
    val totalPages: Int?=null,
    @SerialName("items")
    val items: List<Movie>
)