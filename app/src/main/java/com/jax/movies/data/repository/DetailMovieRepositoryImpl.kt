package com.jax.movies.data.repository

import android.util.Log
import com.jax.movies.data.mapper.FilmsMapper
import com.jax.movies.data.mapper.MoviesMapper
import com.jax.movies.data.remote.api.MoviesApiFactory
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.repository.DetailMovieRepository
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DetailMovieRepositoryImpl : DetailMovieRepository {

    private val apiService = MoviesApiFactory.apiService
    private val filmMapper = FilmsMapper()
    private val movieMapper = MoviesMapper()

    override suspend fun getActors(filmId: Long): Flow<Resource<List<Actor>>> {
        return flow {
            val response = apiService.getActors(filmId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(filmMapper.actorsDtoToEntity(it)))
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
                    emit(Resource.Success(filmMapper.galleriesDtoToEntity(it.items)))
                } ?: emit(Resource.Error(Exception("Response body is null")))
            } else {
                emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
            }
        }
    }

    override suspend fun getSimilarFilms(filmId: Long): Flow<Resource<List<Movie>>> {
        return flow {
            val response = apiService.getSimilarFilms(filmId)
            val filmIdList = response.body()?.films?.map { it.id } ?: emptyList()
            Log.d("asddsadsa", filmIdList.toString())

            val movies = filmIdList.mapNotNull {
                val movieResponse = apiService.getDetailMovie(it)
                if (movieResponse.isSuccessful) movieResponse.body() else null
            }

            val finalMovies = movies.map { movie ->
                movieMapper.detailDtoToEntity(movie)
            }

            if (finalMovies.isNotEmpty()) {
                emit(Resource.Success(finalMovies))
            } else {
                emit(Resource.Error(Exception("No similar movies found")))
            }
        }
    }

    override suspend fun getActorDetailInfo(actorId: Long): Flow<Resource<Actor>> {
        return flow {
            val response = apiService.getActorDetailInfo(actorId)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    val movies = body.films.mapNotNull { filmDto ->
                        val detailResponse = apiService.getDetailMovie(filmDto.filmId)
                        if (detailResponse.isSuccessful) {
                            detailResponse.body()?.let { movieMapper.detailDtoToEntity(it) }
                        } else null
                    }
                    emit(Resource.Success(filmMapper.actorDetailInfoToActor(body, movies)))
                } ?: emit(Resource.Error(Exception("Response body is null")))
            } else {
                emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
            }
        }.catch {
            emit(Resource.Error(it))
        }
    }
}













