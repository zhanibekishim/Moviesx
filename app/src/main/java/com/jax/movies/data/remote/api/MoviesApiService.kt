package com.jax.movies.data.remote.api

import com.jax.movies.BuildConfig
import com.jax.movies.data.remote.model.DetailResponseDto
import com.jax.movies.data.remote.model.MoviesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET("films/premieres")
    suspend fun getPremieres(
        @Query("year") year: Int = 2023,
        @Query("month") month: String = "APRIL",
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<MoviesResponseDto>

    @GET("films/collections")
    suspend fun getCollection(
        @Query("type") type: String,
        @Query("page") page: Int = 1,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<MoviesResponseDto>

    @GET("films/{id}")
    suspend fun getDetailMovie(
        @Path("id") id: Long,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<DetailResponseDto>
}