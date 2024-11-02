package com.jax.movies.data.mapper

import com.jax.movies.data.remote.model.CountryNameContainerDto
import com.jax.movies.data.remote.model.DetailResponseDto
import com.jax.movies.data.remote.model.GenreNameContainerDto
import com.jax.movies.data.remote.model.MovieDto
import com.jax.movies.domain.entity.CountryNameContainer
import com.jax.movies.domain.entity.GenreNameContainer
import com.jax.movies.domain.entity.Movie

class MoviesMapper {

    fun movieDtoToEntity(movieDto: MovieDto): Movie {
        return Movie(
            id = movieDto.id,
            name = movieDto.name,
            year = movieDto.year,
            genres = dtoGenresToEntity(movieDto.genres),
            countries = dtoCountriesToEntity(movieDto.countries),
            posterUrl = movieDto.posterUrl,
            ageLimit = "",
            slogan = "",
            description = "",
            shortDescription = "",
            lengthMovie = ""
        )
    }

    fun detailDtoToEntity(detailDto: DetailResponseDto): Movie {
        return Movie(
            id = detailDto.id,
            name = detailDto.name,
            year = detailDto.year,
            genres = dtoGenresToEntity(detailDto.genres),
            countries = dtoCountriesToEntity(detailDto.countries),
            posterUrl = detailDto.posterUrl,
            ageLimit = detailDto.ageLimit,
            slogan = detailDto.slogan,
            description = detailDto.description,
            shortDescription = detailDto.shortDescription,
            lengthMovie = detailDto.duration
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
}