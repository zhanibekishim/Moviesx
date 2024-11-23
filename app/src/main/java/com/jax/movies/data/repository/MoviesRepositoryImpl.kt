package com.jax.movies.data.repository

import com.jax.movies.data.mapper.MoviesMapper
import com.jax.movies.data.remote.api.MoviesApiService
import com.jax.movies.data.store.OnBoardingSettingStore
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.entity.home.MoviesType
import com.jax.movies.domain.repository.MoviesRepository
import com.jax.movies.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesMapper: MoviesMapper,
    private val apiService: MoviesApiService,
    private val onBoardingSettingStore: OnBoardingSettingStore,
    private val scope: CoroutineScope,
) : MoviesRepository {

    override suspend fun getMovieCollection(type: MoviesType): StateFlow<Resource<List<Movie>>> =
        flow {
            val response = when (type) {
                MoviesType.TOP_250_MOVIES, MoviesType.COMICS_THEME, MoviesType.TOP_POPULAR_MOVIES ->
                    apiService.getCollection(type.name)

                MoviesType.PREMIERS -> apiService.getPremieres()
            }
            if (response.isSuccessful) {
                response.body()?.let { respBody ->
                    emit(Resource.Success(respBody.films.map { moviesMapper.movieDtoToEntity(it) }))
                } ?: emit(Resource.Error(Exception("Response body is null")))
            }
        }.retry(
            retries = RETRY_COUNT,
            predicate = {
                delay(DELAY_TIME)
                true
            }
        ).catch {
            emit(Resource.Error(it))
        }.stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = Resource.Success(emptyList())
        )

    override suspend fun getDetailInfo(movieId: Long): Flow<Resource<Movie>> = flow {
        val response = apiService.getDetailMovie(movieId)
        if (response.isSuccessful) {
            response.body()?.let {
                emit(Resource.Success(moviesMapper.detailDtoToEntity(it)))
            } ?: emit(Resource.Error(Exception("Response body is null")))
        } else {
            emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
        }
    }.retry(
        retries = RETRY_COUNT,
        predicate = {
            delay(DELAY_TIME)
            true
        }
    ).catch {
        emit(Resource.Error(it))
    }

    /*override suspend fun getIsEnteredBeforeValue(): Flow<Resource<Boolean>> =
        onBoardingSettingStore.isEnteredFlow.map { Resource.Success(it) }

    override suspend fun updateIsEntered(isEntered: Boolean) {
        onBoardingSettingStore.updateIsEntered(isEntered)
    }*/


    private companion object {
        const val RETRY_COUNT = 5L
        const val DELAY_TIME = 3000L
    }
}