package com.jax.movies.domain.entity.home

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class Country(
    val id: Int,
    val name: String
) : Parcelable {

    companion object {
        val navType: NavType<Country> = object : NavType<Country>(false) {
            override fun get(bundle: Bundle, key: String): Country? {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(key, Country::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    bundle.getParcelable(key)
                }
            }

            override fun parseValue(value: String): Country {
                return Gson().fromJson(value, Country::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: Country) {
                bundle.putParcelable(key, value)
            }
        }
    }
}