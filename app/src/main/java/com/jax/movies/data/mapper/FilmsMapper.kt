package com.jax.movies.data.mapper

import android.util.Log
import com.jax.movies.data.local.database.MovieCollectionDao
import com.jax.movies.data.local.model.FavouriteMovie
import com.jax.movies.data.local.model.MovieCollectionItemDb
import com.jax.movies.data.local.model.SeenMovie
import com.jax.movies.data.remote.model.films.ActorDetailInfoResponse
import com.jax.movies.data.remote.model.films.ActorDto
import com.jax.movies.data.remote.model.films.GalleryImageContainerDto
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.ActorType
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.components.MovieCollectionItem
import javax.inject.Inject

class FilmsMapper @Inject constructor() {

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
        Log.d("yyyyyyyyyyyyyyyyyyyyyyyy", actor.allMovies.toString())
        Log.d("lllllllllllllllllllllllllllllllllllllllllll", actor.movies.toString())
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

    fun movieEntityToFavourite(movie: Movie): FavouriteMovie {
        return FavouriteMovie(
            id = movie.id,
            name = movie.name,
            year = movie.year,
            lengthMovie = movie.lengthMovie,
            countries = movie.countries,
            genres = movie.genres,
            ratingKp = movie.ratingKp,
            posterUrl = movie.posterUrl,
            slogan = movie.slogan,
            shortDescription = movie.shortDescription,
            description = movie.description,
            ageLimit = movie.ageLimit
        )
    }

    fun movieEntityToSeen(movie: Movie): SeenMovie {
        return SeenMovie(
            id = movie.id,
            name = movie.name,
            year = movie.year,
            lengthMovie = movie.lengthMovie,
            countries = movie.countries,
            genres = movie.genres,
            ratingKp = movie.ratingKp,
            posterUrl = movie.posterUrl,
            slogan = movie.slogan,
            shortDescription = movie.shortDescription,
            description = movie.description,
            ageLimit = movie.ageLimit
        )
    }

    fun favouriteMovieListToMovies(favouriteMovies: List<FavouriteMovie>): List<Movie> {
        return favouriteMovies.map { favouriteMovieToMovie(it) }
    }

    private fun favouriteMovieToMovie(favouriteMovie: FavouriteMovie): Movie {
        return Movie(
            id = favouriteMovie.id,
            name = favouriteMovie.name,
            year = favouriteMovie.year,
            lengthMovie = favouriteMovie.lengthMovie,
            countries = favouriteMovie.countries,
            genres = favouriteMovie.genres,
            ratingKp = favouriteMovie.ratingKp,
            posterUrl = favouriteMovie.posterUrl,
            slogan = favouriteMovie.slogan,
            shortDescription = favouriteMovie.shortDescription,
            description = favouriteMovie.description,
            ageLimit = favouriteMovie.ageLimit
        )
    }

    fun seenMovieListToMovies(seenMovies: List<SeenMovie>): List<Movie> {
        return seenMovies.map { seenMovieToMovie(it) }
    }

    private fun seenMovieToMovie(seenMovie: SeenMovie): Movie {
        return Movie(
            id = seenMovie.id,
            name = seenMovie.name,
            year = seenMovie.year,
            lengthMovie = seenMovie.lengthMovie,
            countries = seenMovie.countries,
            genres = seenMovie.genres,
            ratingKp = seenMovie.ratingKp,
            posterUrl = seenMovie.posterUrl,
            slogan = seenMovie.slogan,
            shortDescription = seenMovie.shortDescription,
            description = seenMovie.description,
            ageLimit = seenMovie.ageLimit
        )
    }

    fun movieCollectionToDb(movieCollectionItem: MovieCollectionItem): MovieCollectionItemDb {
        return MovieCollectionItemDb(
            id = movieCollectionItem.id,
            name = movieCollectionItem.name,
            count = movieCollectionItem.count
        )
    }
    fun movieCollectionDbToEntity(movieCollectionItemDb: MovieCollectionItemDb): MovieCollectionItem {
        return MovieCollectionItem(
            id = movieCollectionItemDb.id,
            name = movieCollectionItemDb.name,
            count = movieCollectionItemDb.count
        )
    }
}
















