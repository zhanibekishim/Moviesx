package com.jax.movies.domain.repository

import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DetailMovieRepository {
    suspend fun getActors(filmId: Long): Flow<Resource<List<Actor>>>
    suspend fun getGalleries(filmId: Long): Flow<Resource<List<GalleryImage>>>
    suspend fun getSimilarFilms(filmId: Long): Flow<Resource<List<Movie>>>

    suspend fun getActorDetailInfo(actorId: Long): Flow<Resource<Actor>>
}