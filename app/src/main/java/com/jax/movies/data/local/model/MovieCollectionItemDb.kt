package com.jax.movies.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jax.movies.utils.Constants.MOVIE_COLLECTION

@Entity(tableName = MOVIE_COLLECTION)
data class MovieCollectionItemDb(
    @PrimaryKey
    val name: String,
    val count: Int,
    val id: Int = 0
)