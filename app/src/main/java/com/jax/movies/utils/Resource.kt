package com.jax.movies.utils

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: Throwable) : Resource<Nothing>()
}
