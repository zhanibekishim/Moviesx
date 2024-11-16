package com.jax.movies.domain.entity.films

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.jax.movies.domain.entity.home.Movie
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class Actor(
    val actorId: Long,
    val nameRu: String,
    val nameEn: String,
    val posterUrl: String,
    val description: String,
    val professionKeys: List<ActorType>,
    val profession: String,
    val movies: List<Movie>,
    val allMovies: Map<ActorType, List<Movie>>,
) : Parcelable {
    companion object {
        val navType: NavType<Actor> = object : NavType<Actor>(isNullableAllowed = false) {
            override fun get(bundle: Bundle, key: String): Actor? {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(key, Actor::class.java)
                } else {
                    bundle.getParcelable(key)
                }
            }

            override fun parseValue(value: String): Actor {
                return Gson().fromJson(value, Actor::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: Actor) {
                bundle.putParcelable(key, value)
            }
        }
    }
}

