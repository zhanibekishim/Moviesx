package com.jax.movies.domain.entity.home

import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class Genre(
    val id: Int,
    val name: String
) : Parcelable {
    companion object {
        val navType: NavType<Genre> = object : NavType<Genre>(false) {
            override fun get(bundle: Bundle, key: String): Genre? {
                @Suppress("DEPRECATION")
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): Genre {
                return Gson().fromJson(value, Genre::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: Genre) {
                bundle.putParcelable(key, value)
            }
        }
    }
}