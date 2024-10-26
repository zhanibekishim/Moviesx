package com.jax.movies.data.repository

import com.jax.movies.data.mapper.MoviesMapper
import com.jax.movies.data.remote.api.MoviesApiFactory
import com.jax.movies.domain.entity.Movie
import com.jax.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MoviesRepositoryImpl : MoviesRepository {

    private val apiService = MoviesApiFactory.apiService
    private val mapper = MoviesMapper()
    private val scope = CoroutineScope(Dispatchers.IO)
    override suspend fun getPremiersList(): StateFlow<Result<List<Movie>>> = flow {
        try {
            val filmsDtoList = apiService.getPremieres(2023, "JANUARY").films
            val films = filmsDtoList.map { mapper.movieDtoToEntity(it) }
            emit(Result.success(films))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
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
}