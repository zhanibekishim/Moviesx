package com.jax.movies.domain.repository

import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.presentation.components.MovieCollectionItem
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DetailMovieRepository {
    suspend fun getActors(filmId: Long): Flow<Resource<List<Actor>>>
    suspend fun getGalleries(filmId: Long): Flow<Resource<List<GalleryImage>>>
    suspend fun getSimilarFilms(filmId: Long): Flow<Resource<List<Movie>>>
    suspend fun getActorDetailInfo(actorId: Long): Flow<Resource<Actor>>

    suspend fun saveFavouriteMovie(movie: Movie): Boolean
    suspend fun saveSeenMovie(movie: Movie): Boolean

    suspend fun deleteFavouriteMovie(movie: Movie)
    suspend fun deleteSeenMovie(movie: Movie)

    suspend fun addNewCollection(collectionItem: MovieCollectionItem): Boolean
    fun getCollection():  Flow<List<MovieCollectionItem>>

    fun checkIsLicked(movieId: Long): Flow<Boolean>
    fun checkIsFavourite(movieId: Long): Flow<Boolean>

}