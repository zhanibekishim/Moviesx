package com.jax.movies.data.remote.api

import com.jax.movies.BuildConfig
import com.jax.movies.data.remote.model.MoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MoviesApiService {
    @GET("premieres")
    suspend fun getPremieres(
        @Query("year") year: Int,
        @Query("month") month: String,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): MoviesResponseDto
}