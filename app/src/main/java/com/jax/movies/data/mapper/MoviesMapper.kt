package com.jax.movies.data.mapper

import com.jax.movies.data.remote.model.home.CountryNameContainerDto
import com.jax.movies.data.remote.model.home.DetailResponseDto
import com.jax.movies.data.remote.model.home.GenreNameContainerDto
import com.jax.movies.data.remote.model.home.MovieDto
import com.jax.movies.domain.entity.home.CountryNameContainer
import com.jax.movies.domain.entity.home.GenreNameContainer
import com.jax.movies.domain.entity.home.Movie
import javax.inject.Inject

class MoviesMapper @Inject constructor() {

    fun movieDtoToEntity(movieDto: MovieDto): Movie {
        return Movie(
            id = movieDto.id,
            name = movieDto.name,
            year = movieDto.year,
            genres = dtoGenresToEntity(movieDto.genres),
            countries = dtoCountriesToEntity(movieDto.countries),
            posterUrl = movieDto.posterUrl,
            ratingKp = movieDto.ratingKp,
            ageLimit = "",
            slogan = "",
            description = "",
            shortDescription = "",
            lengthMovie = ""
        )
    }

    fun detailDtoToEntity(detailDto: DetailResponseDto,id:Long? = null): Movie {
        return Movie(
            id = id?:detailDto.id,
            name = detailDto.name?:"",
            year = detailDto.year,
            genres = dtoGenresToEntity(detailDto.genres),
            countries = dtoCountriesToEntity(detailDto.countries),
            posterUrl = detailDto.posterUrl?:"",
            ageLimit = detailDto.ageLimit+"+",
            slogan = detailDto.slogan?:"",
            ratingKp = detailDto.ratingKp,
            description = detailDto.description?:"",
            shortDescription = detailDto.shortDescription?:"",
            lengthMovie = detailDto.duration.convertMinutesToAccepted()
        )
    }

    private fun dtoGenresToEntity(genres: List<GenreNameContainerDto>): List<GenreNameContainer> {
        return genres.map {
            GenreNameContainer(it.genre)
        }
    }

    private fun dtoCountriesToEntity(countries: List<CountryNameContainerDto>): List<CountryNameContainer> {
        return countries.map {
            CountryNameContainer(it.country)
        }
    }

    private fun Long.convertMinutesToAccepted(): String {
        val hours = this / 60
        val minutes = this % 60
        return "$hours hour${if (hours != 1L) "s" else ""} ${minutes} minute${if (minutes != 1L) "s" else ""}"
    }
}