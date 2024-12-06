package com.jax.movies.presentation.profile

data class CollectionItem(
    val title: String,
    val collectionTypeIcon: Int,
    var count: Int,
    var default: Boolean = true
)
