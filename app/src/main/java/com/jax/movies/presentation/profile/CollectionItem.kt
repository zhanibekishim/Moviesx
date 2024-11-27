package com.jax.movies.presentation.profile

data class CollectionItem(
    val title: String,
    val collectionTypeIcon: Int,
    val count: Int,
    val default: Boolean = true
)
