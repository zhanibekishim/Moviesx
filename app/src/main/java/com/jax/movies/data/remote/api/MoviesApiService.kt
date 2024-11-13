package com.jax.movies.data.remote.api

import com.jax.movies.BuildConfig
import com.jax.movies.data.remote.model.films.GalleryResponse
import com.jax.movies.data.remote.model.films.SimilarFilmsResponse
import com.jax.movies.data.remote.model.films.StaffsResponse
import com.jax.movies.data.remote.model.home.DetailResponseDto
import com.jax.movies.data.remote.model.home.MoviesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET("films/premieres")
    suspend fun getPremieres(
        @Query("year") year: Int = DEFAULT_YEAR,
        @Query("month") month: String = DEFAULT_MONTH,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<MoviesResponseDto>

    @GET("films/collections")
    suspend fun getCollection(
        @Query("type") type: String,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<MoviesResponseDto>

    @GET("films/{id}")
    suspend fun getDetailMovie(
        @Path("id") id: Long,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<DetailResponseDto>

    @GET("staff/{filmId}")
    suspend fun getActors(
        @Path("filmId") id: Long,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<StaffsResponse>

    @GET("films/{filmId}")
    suspend fun getGallery(
        @Path("filmId") id: Long,
        @Query("images") type: String = GALLERY_TYPE,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<GalleryResponse>

    @GET("films/{filmId}/similars")
    suspend fun getSimilarFilms(
        @Path("filmId") id: Long,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<SimilarFilmsResponse>

    private companion object{
        const val GALLERY_TYPE = "STILL"
        const val DEFAULT_PAGE = 1
        const val DEFAULT_MONTH = "APRIL"
        const val DEFAULT_YEAR = 2023
    }
}