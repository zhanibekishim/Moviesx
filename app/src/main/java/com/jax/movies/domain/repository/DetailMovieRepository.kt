package com.jax.movies.domain.repository

import com.jax.movies.domain.entity.Actor
import com.jax.movies.domain.entity.GalleryImage
import com.jax.movies.domain.entity.SimilarMovie
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DetailMovieRepository {
    suspend fun getActors(filmId: Long): Flow<Resource<List<Actor>>>
    suspend fun getGalleries(filmId: Long): Flow<Resource<List<GalleryImage>>>
    suspend fun getSimilarFilms(filmId: Long): Flow<Resource<List<SimilarMovie>>>
}