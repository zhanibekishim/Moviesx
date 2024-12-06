package com.jax.movies.data.remote.api

import com.jax.movies.BuildConfig
import com.jax.movies.data.remote.model.films.ActorDetailInfoResponse
import com.jax.movies.data.remote.model.films.ActorDto
import com.jax.movies.data.remote.model.films.CountriesDto
import com.jax.movies.data.remote.model.films.GalleryResponse
import com.jax.movies.data.remote.model.films.GenresDto
import com.jax.movies.data.remote.model.films.SimilarFilmsResponse
import com.jax.movies.data.remote.model.home.DetailResponseDto
import com.jax.movies.data.remote.model.home.MoviesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET("v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("year") year: Int = DEFAULT_YEAR,
        @Query("month") month: String = DEFAULT_MONTH,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<MoviesResponseDto>

    @GET("v2.2/films/collections")
    suspend fun getCollection(
        @Query("type") type: String,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("ratingFrom") minRating: Int = MINIMUM_RATING_MOVIE,
        @Query("ratingTo") maxRating: Int = MAXIMUM_RATING_MOVIE,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<MoviesResponseDto>

    @GET("v2.2/films/{id}")
    suspend fun getDetailMovie(
        @Path("id") id: Long,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<DetailResponseDto>

    @GET("v1/staff")
    suspend fun getActors(
        @Query("filmId") id: Long,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<List<ActorDto>>

    @GET("v2.2/films/{filmId}/images")
    suspend fun getGallery(
        @Path("filmId") id: Long,
        @Query("type") type: String = GALLERY_TYPE,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<GalleryResponse>

    @GET("v2.2/films/{filmId}/similars")
    suspend fun getSimilarFilms(
        @Path("filmId") id: Long,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<SimilarFilmsResponse>

    @GET("v1/staff/{id}")
    suspend fun getActorDetailInfo(
        @Path("id") id: Long,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<ActorDetailInfoResponse>

    /*    @GET("v2.1/films/search-by-keyword")
        suspend fun searchByQuery(
            @Query("keyword") query: String,
            @Query("page") page: Int = DEFAULT_PAGE,
            @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
        ):Response<SearchResponseDto>*/

    @GET("v2.2/films")
    suspend fun searchByQuery(
        @Query("keyword") query: String,
        @Query("ratingFrom") fromRating: Int = MINIMUM_RATING_MOVIE,
        @Query("ratingTo") toRating: Int = MAXIMUM_RATING_MOVIE,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("genres") genres: String = DEFAULT_GENRES,
        @Query("countries") countries: String = DEFAULT_COUNTRIES,
        @Query("order") order: String = DEFAULT_ORDER,
        @Query("type") type: String = DEFAULT_TYPE,
        @Query("yearFrom") yearFrom: Int = DEFAULT_YEAR,
        @Query("yearTo") yearTo: Int = DEFAULT_YEAR,

        /*@Query("order") order: String = DEFAULT_ORDER,*/
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<MoviesResponseDto>

    @GET("v2.2/films/filters")
    suspend fun getGenres(
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<GenresDto>

    @GET("v2.2/films/filters")
    suspend fun getCountries(
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<CountriesDto>

    private companion object {
        const val GALLERY_TYPE = "STILL"
        const val DEFAULT_PAGE = 1
        const val DEFAULT_MONTH = "APRIL"
        const val DEFAULT_YEAR = 2023
        const val DEFAULT_ORDER = "RATING"
        const val DEFAULT_TYPE = "FILM"

        val DEFAULT_COUNTRIES = "1"
        val DEFAULT_GENRES = "1"

        const val MINIMUM_RATING_MOVIE = 8
        const val MAXIMUM_RATING_MOVIE = 10
    }
}
