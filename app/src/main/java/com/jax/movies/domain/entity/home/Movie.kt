package com.jax.movies.domain.entity.home

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class Movie(
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
) : Parcelable {
    companion object {
        val navType: NavType<Movie> = object : NavType<Movie>(isNullableAllowed = false) {
            override fun get(bundle: Bundle, key: String): Movie? {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(key, Movie::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    bundle.getParcelable(key)
                } ?: throw IllegalArgumentException("Movie not found for key: $key")
            }

            override fun parseValue(value: String): Movie {
                return Gson().fromJson(value, Movie::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: Movie) {
                bundle.putParcelable(key, value)
            }
        }

    }
}



