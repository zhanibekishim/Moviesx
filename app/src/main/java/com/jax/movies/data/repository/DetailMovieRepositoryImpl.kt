package com.jax.movies.data.repository

import com.jax.movies.data.mapper.FilmsMapper
import com.jax.movies.data.remote.api.MoviesApiFactory
import com.jax.movies.domain.entity.Actor
import com.jax.movies.domain.entity.GalleryImage
import com.jax.movies.domain.entity.SimilarMovie
import com.jax.movies.domain.repository.DetailMovieRepository
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailMovieRepositoryImpl : DetailMovieRepository {

    private val apiService = MoviesApiFactory.apiService
    private val mapper = FilmsMapper()

    override suspend fun getActors(filmId: Long): Flow<Resource<List<Actor>>> {
        return flow {
            val response = apiService.getActors(filmId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(mapper.actorsDtoToEntity(it.actors)))
                } ?: emit(Resource.Error(Exception("Response body is null")))
            } else {
                emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
            }
        }
    }

    override suspend fun getGalleries(filmId: Long): Flow<Resource<List<GalleryImage>>> {
        return flow {
            val response = apiService.getGallery(filmId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(mapper.galleriesDtoToEntity(it.items)))
                } ?: emit(Resource.Error(Exception("Response body is null")))
            } else {
                emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
            }
        }
    }

    override suspend fun getSimilarFilms(filmId: Long): Flow<Resource<List<SimilarMovie>>> {
        return flow {
            val response = apiService.getSimilarFilms(filmId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(mapper.similarFilmsDtoToEntity(it.films)))
                } ?: emit(Resource.Error(Exception("Response body is null")))
            } else {
                emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
            }
        }
    }
}