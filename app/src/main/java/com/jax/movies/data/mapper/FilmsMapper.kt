package com.jax.movies.data.mapper

import android.util.Log
import com.jax.movies.data.remote.model.films.ActorDetailInfoResponse
import com.jax.movies.data.remote.model.films.ActorDto
import com.jax.movies.data.remote.model.films.FilmDto
import com.jax.movies.data.remote.model.films.GalleryImageContainerDto
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.ActorType
import com.jax.movies.domain.entity.films.Film
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie
import javax.inject.Inject

class FilmsMapper @Inject constructor(){

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
            professionKeys = professionsToList(this.professionKey),
            profession = this.professionKey,
            posterUrl = this.posterUrl,
            movies = emptyList(),
            allMovies = emptyMap()
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

    fun actorDetailInfoToActor(
        actorDto: ActorDetailInfoResponse,
        movies: Map<ActorType, List<Movie>>
    ): Actor {
        val professionKeys = professionsToList(actorDto.professions)

        Log.d("ActorMapping", "Movies Map: $movies")
        Log.d("ActorMapping", "Profession Keys: $professionKeys")

        val selectedMovies = movies[professionKeys[0]] ?: emptyList()
        Log.d("ActorMapping", "Selected Movies: $selectedMovies")

        val actor = Actor(
            actorId = actorDto.actorId,
            nameEn = actorDto.nameEn,
            nameRu = actorDto.nameRu,
            posterUrl = actorDto.posterUrl,
            description = "",
            professionKeys = professionKeys,
            profession = actorDto.professions,
            movies = selectedMovies,
            allMovies = movies
        )
       /* Log.d("actorssssssssssssssssssssssssssss",actor.toString())*/
        Log.d("yyyyyyyyyyyyyyyyyyyyyyyy",actor.allMovies.toString())
        Log.d("lllllllllllllllllllllllllllllllllllllllllll",actor.movies.toString())
        return actor
    }


    fun professionsToList(professions: String): List<ActorType> {
        return professions.split(",").map { profession ->
            val trimmedProfession = profession.trim()
            Log.d("ActorMapping - professionsToList", "Profession Keys: $trimmedProfession")
            when (trimmedProfession) {
                "WRITE" -> ActorType.WRITE
                "OPERATOR" -> ActorType.OPERATOR
                "EDITOR" -> ActorType.EDITOR
                "COMPOSER" -> ActorType.COMPOSER
                "PRODUCER_USSR" -> ActorType.PRODUCER_USSR
                "TRANSLATOR" -> ActorType.TRANSLATOR
                "DIRECTOR" -> ActorType.DIRECTOR
                "DESIGN" -> ActorType.DESIGN
                "PRODUCER" -> ActorType.PRODUCER
                "Актер" -> ActorType.ACTOR
                "VOICE_DIRECTOR" -> ActorType.VOICE_DIRECTOR
                "UNKNOWN" -> ActorType.UNKNOWN
                else -> ActorType.UNKNOWN
            }
        }
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