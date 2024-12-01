package com.jax.movies.data.local.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jax.movies.domain.entity.home.CountryNameContainer
import com.jax.movies.domain.entity.home.GenreNameContainer
import com.jax.movies.utils.Constants.FAVOURITE_MOVIES_TABLE

@Immutable
@Entity(tableName = FAVOURITE_MOVIES_TABLE)
data class FavouriteMovie(
    @PrimaryKey
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
