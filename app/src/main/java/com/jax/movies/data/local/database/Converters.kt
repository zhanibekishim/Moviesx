package com.jax.movies.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jax.movies.domain.entity.home.CountryNameContainer
import com.jax.movies.domain.entity.home.GenreNameContainer

class Converters {

    @TypeConverter
    fun fromGenreList(genres: List<GenreNameContainer>): String {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun toGenreList(genresString: String): List<GenreNameContainer> {
        val type = object : TypeToken<List<GenreNameContainer>>() {}.type
        return Gson().fromJson(genresString, type)
    }

    @TypeConverter
    fun fromCountriesLIst(countries: List<CountryNameContainer>): String {
        return Gson().toJson(countries)
    }

    @TypeConverter
    fun toCountriesList(countriesString: String): List<CountryNameContainer> {
        val type = object : TypeToken<List<CountryNameContainer>>() {}.type
        return Gson().fromJson(countriesString, type)
    }
}