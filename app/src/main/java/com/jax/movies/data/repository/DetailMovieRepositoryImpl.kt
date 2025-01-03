package com.jax.movies.data.repository

import android.util.Log
import com.jax.movies.data.local.database.FavouriteMoviesDao
import com.jax.movies.data.local.database.MovieCollectionDao
import com.jax.movies.data.local.database.SeenMoviesDao
import com.jax.movies.data.mapper.FilmsMapper
import com.jax.movies.data.mapper.MoviesMapper
import com.jax.movies.data.remote.api.MoviesApiService
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.ActorType
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.repository.DetailMovieRepository
import com.jax.movies.presentation.components.MovieCollectionItem
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailMovieRepositoryImpl @Inject constructor(
    private val apiService: MoviesApiService,
    private val filmMapper: FilmsMapper,
    private val movieMapper: MoviesMapper,
    private val seenMoviesDao: SeenMoviesDao,
    private val favouriteMoviesDao: FavouriteMoviesDao,
    private val movieCollectionDao: MovieCollectionDao
) : DetailMovieRepository {

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

            val finalMovies = movies.mapIndexed { index, movie ->
                movieMapper.detailDtoToEntity(movie, filmIdList[index])
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
                    val resultMap = mutableMapOf<ActorType, MutableList<Movie>>()

                    body.films.take(5).forEach { filmDto ->
                        val detailResponse = apiService.getDetailMovie(filmDto.filmId)
                        if (detailResponse.isSuccessful) {
                            val movie =
                                detailResponse.body()?.let { movieMapper.detailDtoToEntity(it) }
                            if (movie != null) {

                                val actorType = filmMapper.professionsToList(filmDto.professionKey)

                                actorType.forEach { type ->
                                    resultMap.getOrPut(type) { mutableListOf() }.add(movie)
                                }
                            }
                        }
                    }
                    Log.d("sdadasdasdsasaasdasd", resultMap.toString())


                    if (resultMap.isNotEmpty()) {
                        Log.d(
                            "sdadasdasdsasadasdasdasda",
                            filmMapper.actorDetailInfoToActor(body, resultMap).toString()
                        )
                        emit(Resource.Success(filmMapper.actorDetailInfoToActor(body, resultMap)))
                    } else {
                        emit(Resource.Error(Exception("Movies list is empty")))
                    }
                } ?: emit(Resource.Error(Exception("Response body is null")))
            } else {
                emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
            }
        }.catch {
            emit(Resource.Error(it))
        }
    }

    override suspend fun saveFavouriteMovie(movie: Movie): Boolean {
        val favouriteMovie = filmMapper.movieEntityToFavourite(movie)
        val insertedId = favouriteMoviesDao.saveFavouriteMovie(favouriteMovie)
        return insertedId != -1L
    }

    override suspend fun saveSeenMovie(movie: Movie): Boolean {
        val seenMovie = filmMapper.movieEntityToSeen(movie)
        val insertedId = seenMoviesDao.saveSeenMovie(seenMovie)
        return insertedId != -1L
    }

    override suspend fun deleteFavouriteMovie(movie: Movie) {
        favouriteMoviesDao.deleteFavouriteMovie(filmMapper.movieEntityToFavourite(movie))
    }

    override suspend fun deleteSeenMovie(movie: Movie) {
        seenMoviesDao.deleteSeenMovie(filmMapper.movieEntityToSeen(movie))
    }

    override suspend fun addNewCollection(collectionItem: MovieCollectionItem): Boolean {
/*        val movieCollection = filmMapper.movieCollectionToDb(collectionItem)
        val insertedId = movieCollectionDao.insertMovieCollection(movieCollection)
        return insertedId != -1L*/
        val movieCollection = filmMapper.movieCollectionToDb(collectionItem)
        val insertedId = movieCollectionDao.insertMovieCollection(collectionItem)
        return false
    }

    override fun getCollection(): Flow<List<MovieCollectionItem>> {
        return movieCollectionDao.getAllMovieCollections()/*flow {
            movieCollectionDao.getAllMovieCollections().collect{
                val collection = it.map { e->filmMapper.movieCollectionDbToEntity(e) }
                emit(collection)
            }
        }*/
    }

    override fun checkIsLicked(movieId: Long): Flow<Boolean> {
         return favouriteMoviesDao.exists(movieId)
    }

    override fun checkIsFavourite(movieId: Long): Flow<Boolean> {
        return seenMoviesDao.exists(movieId)
    }
}













