package com.jax.movies.data.repository

import com.jax.movies.data.mapper.MoviesMapper
import com.jax.movies.data.remote.api.MoviesApiFactory
import com.jax.movies.domain.entity.Movie
import com.jax.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

class MoviesRepositoryImpl : MoviesRepository {

    private val apiService = MoviesApiFactory.apiService
    private val mapper = MoviesMapper()
    private val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun getMovieCollection(type: String): StateFlow<Result<List<Movie>>> = flow {
        val response = apiService.getCollection(type)
        val movies = response.films.map { mapper.movieDtoToEntity(it) }
        emit(Result.success(movies))
    }.retry(
        retries = RETRY_COUNT,
        predicate = {
            delay(DELAY_TIME)
            true
        }
    ).catch {
        emit(Result.failure(it))
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = Result.success(emptyList())
    )

    override suspend fun getDetailInfo(id: Long): Result<Movie> {
        return try {
            val detailInfo = apiService.getDetailMovie(id)
            val movie = mapper.detailDtoToEntity(detailInfo)
            Result.success(movie)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private companion object {
        const val RETRY_COUNT = 5L
        const val DELAY_TIME = 3000L
    }
}