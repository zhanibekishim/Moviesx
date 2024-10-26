package com.jax.movies.data.remote.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesApiFactory {

    companion object {
        private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/"

        private val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()


        private val retrofit = Retrofit.Builder()
           .baseUrl(BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .client(client)
           .build()

        val apiService = retrofit.create(MoviesApiService::class.java)
    }
}