package com.jax.movies.data.mapper

import com.jax.movies.data.remote.model.films.ActorDto
import com.jax.movies.data.remote.model.films.GalleryImageContainerDto
import com.jax.movies.data.remote.model.films.SimilarMovieDto
import com.jax.movies.domain.entity.Actor
import com.jax.movies.domain.entity.GalleryImage
import com.jax.movies.domain.entity.SimilarMovie

class FilmsMapper {

    fun actorsDtoToEntity(actorsDto: List<ActorDto>): List<Actor> {
        return actorsDto.map { actorDto ->
            actorDtoToEntity(actorDto)
        }
    }

    private fun actorDtoToEntity(actorDto: ActorDto): Actor {
        return Actor(
            actorId = actorDto.actorId,
            nameRu = actorDto.nameRu ?: "",
            nameEn = actorDto.nameEn ?: "",
            description = actorDto.description ?: "",
            professionText = actorDto.professionText,
            professionKey = actorDto.professionKey,
        )
    }

    fun galleriesDtoToEntity(galleries: List<GalleryImageContainerDto>): List<GalleryImage> {
        return galleries.map { galleryImageDto ->
            galleryImageToEntity(galleryImageDto)
        }
    }

    private fun galleryImageToEntity(galleryImageDto: GalleryImageContainerDto): GalleryImage {
        return GalleryImage(
            imageUrl = galleryImageDto.imageUrl
        )
    }

    fun similarFilmsDtoToEntity(similarFilmsDto: List<SimilarMovieDto>): List<SimilarMovie> {
        return similarFilmsDto.map { similarMovieDto ->
            similarFilmDtoToEntity(similarMovieDto)
        }
    }

    private fun similarFilmDtoToEntity(similarMovieDto: SimilarMovieDto): SimilarMovie {
        return SimilarMovie(
            id = similarMovieDto.id,
            nameRu = similarMovieDto.nameRu ?: "",
            nameEn = similarMovieDto.nameEn ?: "",
            posterUrl = similarMovieDto.posterUrl,
        )
    }
}