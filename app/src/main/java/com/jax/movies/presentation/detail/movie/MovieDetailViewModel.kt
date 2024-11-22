package com.jax.movies.presentation.detail.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.ActorType
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.usecase.GetActorsUseCaseImpl
import com.jax.movies.domain.usecase.GetDetailMovieUseCaseImpl
import com.jax.movies.domain.usecase.GetGalleriesUseCaseImpl
import com.jax.movies.domain.usecase.GetSimilarMoviesUseCaseImpl
import com.jax.movies.utils.Resource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {
    private val getDetailMovieUseCaseImpl = GetDetailMovieUseCaseImpl()
    private val getActorsUseCaseImpl = GetActorsUseCaseImpl()
    private val getGalleriesUseCaseImpl = GetGalleriesUseCaseImpl()
    private val getSimilarMoviesUseCaseImpl = GetSimilarMoviesUseCaseImpl()

    private val _state = MutableStateFlow<MovieDetailState>(MovieDetailState.Initial)
    val state: StateFlow<MovieDetailState> = _state.asStateFlow()

    private val _movieNavigationChannel = Channel<MovieScreenIntent>(capacity = Channel.CONFLATED)
    val movieNavigationChannel = _movieNavigationChannel.receiveAsFlow()

    fun handleIntent(intent: MovieScreenIntent){
        when(intent){
            is MovieScreenIntent.OnActorClick -> {
                viewModelScope.launch {
                    _movieNavigationChannel.send(MovieScreenIntent.OnActorClick(intent.actor))
                }
            }
            is MovieScreenIntent.OnBlindEyeClick -> {
                viewModelScope.launch {
                    _movieNavigationChannel.send(MovieScreenIntent.OnBlindEyeClick)
                }
            }
            is MovieScreenIntent.OnFavouriteClick -> {
                viewModelScope.launch {
                    _movieNavigationChannel.send(MovieScreenIntent.OnFavouriteClick)
                }
            }
            is MovieScreenIntent.OnGalleryClick -> {
                viewModelScope.launch {
                    _movieNavigationChannel.send(MovieScreenIntent.OnGalleryClick(intent.movie))
                }
            }
            is MovieScreenIntent.OnLickClick -> {
                viewModelScope.launch {
                    _movieNavigationChannel.send(MovieScreenIntent.OnLickClick)
                }
            }
            is MovieScreenIntent.OnMoreClick -> {
                viewModelScope.launch {
                    _movieNavigationChannel.send(MovieScreenIntent.OnMoreClick)
                }
            }
            is MovieScreenIntent.OnMovieClick -> {
                viewModelScope.launch {
                    _movieNavigationChannel.send(MovieScreenIntent.OnMovieClick(
                        fromMovie = intent.fromMovie,
                        toMovie = intent.toMovie
                    ))
                }
            }
            is MovieScreenIntent.OnShareClick -> {
                viewModelScope.launch {
                    _movieNavigationChannel.send(MovieScreenIntent.OnShareClick)
                }
            }

            is MovieScreenIntent.OnBackClicked -> {
                viewModelScope.launch {
                    _movieNavigationChannel.send(MovieScreenIntent.OnBackClicked(intent.movie))
                }
            }
            is MovieScreenIntent.Default -> {}
        }
    }

    fun handleAction(action: MovieScreenAction){
        when(action){
            is MovieScreenAction.FetchMovieDetailInfo ->{
                fetchDetailInfo(action.movie)
                Log.d("dsdasdasdasdasssssssssssssssssssssssssssssssssssssssss",action.movie.id.toString())
            }
        }
    }
    private fun fetchDetailInfo(movie: Movie) {
        _state.value = MovieDetailState.Loading
        val movieDeferred: Deferred<Movie> = viewModelScope.async {
            var finalMovie = movie
            Log.d("dsdasdasdasdasssssssssssssssssssssssssssssssssssssssss",movie.id.toString())
            getDetailMovieUseCaseImpl(movie.id).first { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = MovieDetailState.Error(result.message.toString())
                        return@first true
                    }

                    is Resource.Success -> {
                        finalMovie = result.data
                        finalMovie = finalMovie.copy(id = movie.id)
                        return@first true
                    }
                }
            }
            Log.d("dsadsadas",finalMovie.toString())
            finalMovie
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
            Log.d("dsadsadas",actorsList.toString())
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
            Log.d("dsadsadas",galleriesList.toString())
            galleriesList
        }
        val similarMoviesDeferred = viewModelScope.async {
            var similarMoviesList: List<Movie> = emptyList()
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
            Log.d("dsadsadas",similarMoviesList.toString())
            similarMoviesList
        }
        viewModelScope.launch {
            val finalMovie = movieDeferred.await()
            val actorsList = actorsDeferred.await()
            val galleriesList = galleriesDeferred.await()
            val similarMoviesList = similarMoviesDeferred.await()
            val filmCrew = getFilmCrew(actorsList)
            _state.value = MovieDetailState.Success(
                movie = finalMovie,
                actors = actorsList,
                filmCrew = filmCrew,
                gallery = galleriesList,
                similarMovies = similarMoviesList
            )
        }
    }
    private fun getFilmCrew(actors: List<Actor>): List<Actor> {
        return actors.filter { actor ->
            when (actor.profession) {
                ActorType.DIRECTOR.name,
                ActorType.WRITE.name,
                ActorType.PRODUCER.name,
                ActorType.OPERATOR.name,
                ActorType.DESIGN.name,
                ActorType.VOICE_DIRECTOR.name,
                ActorType.PRODUCER_USSR.name -> true
                else -> false
            }
        }
    }
}















