package com.jax.movies.presentation.detail.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.entity.Actor
import com.jax.movies.domain.entity.GalleryImage
import com.jax.movies.domain.entity.Movie
import com.jax.movies.domain.entity.SimilarMovie
import com.jax.movies.domain.usecase.GetActorsUseCaseImpl
import com.jax.movies.domain.usecase.GetDetailMovieUseCaseImpl
import com.jax.movies.domain.usecase.GetGalleriesUseCaseImpl
import com.jax.movies.domain.usecase.GetSimilarMoviesUseCaseImpl
import com.jax.movies.utils.Resource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Suppress("NAME_SHADOWING")
class MovieDetailViewModel : ViewModel() {
    private val getDetailMovieUseCaseImpl = GetDetailMovieUseCaseImpl()
    private val getActorsUseCaseImpl = GetActorsUseCaseImpl()
    private val getGalleriesUseCaseImpl = GetGalleriesUseCaseImpl()
    private val getSimilarMoviesUseCaseImpl = GetSimilarMoviesUseCaseImpl()

    private val _state = MutableStateFlow<MovieDetailState>(MovieDetailState.Initial)
    val state: StateFlow<MovieDetailState> get() = _state.asStateFlow()

    fun fetchDetailInfo(movie: Movie) {
        _state.value = MovieDetailState.Loading
        val movieDeferred: Deferred<Movie> = viewModelScope.async {
            var movie = Movie(
                id = -1,
                name = "",
                year = 0,
                posterUrl = "",
                ratingKp = 0.0,
                slogan = "",
                shortDescription = "",
                description = "",
                lengthMovie = "",
                ageLimit = "",
                genres = emptyList(),
                countries = emptyList()
            )
            getDetailMovieUseCaseImpl(movie.id).first { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = MovieDetailState.Error(result.message.toString())
                        return@first true
                    }
                    is Resource.Success -> {
                        movie = result.data
                        return@first true
                    }
                }
            }
            movie
        }

        val actorsDeferred: Deferred<List<Actor>> = viewModelScope.async {
            var actorsList: List<Actor> = emptyList()
            getActorsUseCaseImpl(movie.id).first { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = MovieDetailState.Error(result.message.toString())
                        return@first true
                    }
                    is Resource.Success -> {
                        actorsList = result.data
                        return@first true
                    }
                }
            }
            actorsList
        }
        val galleriesDeferred = viewModelScope.async {
            var galleriesList: List<GalleryImage> = emptyList()
            getGalleriesUseCaseImpl(movie.id).first { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = MovieDetailState.Error(result.message.toString())
                        return@first true
                    }
                    is Resource.Success -> {
                        galleriesList = result.data
                        return@first true
                    }
                }
            }
            galleriesList
        }
        val similarMoviesDeferred = viewModelScope.async {
            var similarMoviesList: List<SimilarMovie> = emptyList()
            getSimilarMoviesUseCaseImpl(movie.id).first { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = MovieDetailState.Error(result.message.toString())
                        return@first true
                    }
                    is Resource.Success -> {
                        similarMoviesList = result.data
                        return@first true
                    }
                }
            }
            similarMoviesList
        }
        viewModelScope.launch {
            val movie = movieDeferred.await()
            val actorsList = actorsDeferred.await()
            val galleriesList = galleriesDeferred.await()
            val similarMoviesList = similarMoviesDeferred.await()
            _state.value = MovieDetailState.Success(
                movie = movie,
                actors = actorsList,
                gallery = galleriesList,
                similarMovies = similarMoviesList
            )
        }
    }
}
