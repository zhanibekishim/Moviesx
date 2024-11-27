package com.jax.movies.data.local.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import com.jax.movies.domain.entity.home.CountryNameContainer
import com.jax.movies.domain.entity.home.GenreNameContainer
import com.jax.movies.utils.Constants.SEEN_MOVIES_TABLE

@Immutable
@Entity(tableName = SEEN_MOVIES_TABLE)
data class SeenMovie(
    val id: Long,
    val name: String,
    val year: Int,
    val posterUrl: String,
    val ratingKp: Double,
    val slogan: String,
    val shortDescription: String,
    val description: String,
    val lengthMovie: String,
    val ageLimit: String,
    val genres: List<GenreNameContainer>,
    val countries: List<CountryNameContainer>,
)

