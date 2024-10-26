package com.jax.movies.data.mapper

import com.jax.movies.data.remote.model.DetailResponse
import com.jax.movies.data.remote.model.MovieDto
import com.jax.movies.domain.entity.Movie

class MoviesMapper {

    fun movieDtoToEntity(movieDto: MovieDto):Movie{
        return Movie(
            id = movieDto.id,
            name = movieDto.name,
            year = movieDto.year,
            genres = movieDto.genres,
            countries = movieDto.countries,
            posterUrl = movieDto.posterUrl,
            ageLimit = "",
            slogan = "",
            description = "",
            shortDescription = "",
            lengthMovie = ""
        )
    }
    fun detailDtoToEntity(detailDto: DetailResponse):Movie{
        return Movie(
            id = detailDto.id,
            name = detailDto.name,
            year = detailDto.year,
            genres = detailDto.genres,
            countries = detailDto.countries,
            posterUrl = detailDto.posterUrl,
            ageLimit = detailDto.ageLimit,
            slogan = detailDto.slogan,
            description = detailDto.description,
            shortDescription = detailDto.shortDescription,
            lengthMovie = detailDto.duration
        )
    }
}