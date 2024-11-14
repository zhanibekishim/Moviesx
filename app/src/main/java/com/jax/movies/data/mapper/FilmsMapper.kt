package com.jax.movies.data.mapper

import com.jax.movies.data.remote.model.films.ActorDetailInfoResponse
import com.jax.movies.data.remote.model.films.ActorDto
import com.jax.movies.data.remote.model.films.FilmDto
import com.jax.movies.data.remote.model.films.GalleryImageContainerDto
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.Film
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie

class FilmsMapper {

    fun actorsDtoToEntity(actorsDto: List<ActorDto>?): List<Actor> {
        return actorsDto?.map {
            it.actorDtoToEntity()
        } ?: emptyList()
    }

    private fun ActorDto.actorDtoToEntity(): Actor {
        return Actor(
            actorId = this.actorId,
            nameRu = this.nameRu ?: "",
            nameEn = this.nameEn ?: "",
            description = this.description ?: "",
            professionKeys = this.professionText.professionsToList(),
            profession = this.professionKey,
            posterUrl = this.posterUrl,
            movies = emptyList()
        )
    }

    fun galleriesDtoToEntity(galleries: List<GalleryImageContainerDto>?): List<GalleryImage> {
        return galleries?.map { galleryImageDto ->
            galleryImageToEntity(galleryImageDto)
        } ?: emptyList()
    }

    private fun galleryImageToEntity(galleryImageDto: GalleryImageContainerDto): GalleryImage {
        return GalleryImage(
            imageUrl = galleryImageDto.imageUrl ?: ""
        )
    }

    fun actorDetailInfoToActor(actorDto: ActorDetailInfoResponse,movies:List<Movie>): Actor {
       /* val films = actorDto.films.map { it.filmDtoToEntity() }*/
        return Actor(
            actorId = actorDto.actorId,
            nameEn = actorDto.nameEn,
            nameRu = actorDto.nameRu,
            posterUrl = actorDto.posterUrl,
            description = "",
            professionKeys = actorDto.professions.professionsToList(),
            profession = "",
            movies = movies
        )
    }

    private fun String.professionsToList(): List<String> {
        return this.split(",").toList()
    }

    private fun FilmDto.filmDtoToEntity(): Film {
        return Film(
            filmId = this.filmId,
            nameRu = this.nameRu,
            nameEn = this.nameEn,
            rating = this.rating,
            description = this.description,
            professionKey = this.professionKey,
        )
    }
}