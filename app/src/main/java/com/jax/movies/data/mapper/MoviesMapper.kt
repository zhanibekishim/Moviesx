package com.jax.movies.data.mapper

import com.jax.movies.data.remote.model.MovieDto
import com.jax.movies.domain.entity.Movie

class MoviesMapper {

    fun dtoToEntity(movieDto: MovieDto):Movie{
        return Movie(
            id = movieDto.id,
            name = movieDto.name,
            year = movieDto.year,
            genres = movieDto.genres,
            countries = movieDto.countries,
            posterUrl = movieDto.posterUrl
        )
    }
}