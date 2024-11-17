package com.jax.movies.presentation.detail.filmography

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.ActorType
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.usecase.GetActorDetailInfoUseCaseImpl
import com.jax.movies.utils.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FilmographyViewModel : ViewModel() {

    private val getActorDetailInfoUseCase = GetActorDetailInfoUseCaseImpl()

    var actorTypes: Map<ActorType, List<Movie>> = emptyMap()
    private val _state = MutableStateFlow<FilmographyScreenState>(FilmographyScreenState.Initial)
    val state = _state.asStateFlow()
    private val _filmographyNavigationChannel = Channel<FilmographyScreenIntent>()
    val filmographyNavigationChannel = _filmographyNavigationChannel.receiveAsFlow()

  /*  init {
        fetchFilmography()
        Log.d("akumaaaaaaaaaaaaaaaaaaaaa", actor.toString())
    }*/

    fun handleIntent(intent: FilmographyScreenIntent){
        when(intent){
            is FilmographyScreenIntent.Default -> {}
            is FilmographyScreenIntent.OnClickBack -> {
                viewModelScope.launch {
                    _filmographyNavigationChannel.send(FilmographyScreenIntent.OnClickBack)
                }
            }
            is FilmographyScreenIntent.OnMovieClick -> {
                viewModelScope.launch {
                    _filmographyNavigationChannel.send(FilmographyScreenIntent.OnMovieClick(intent.movie))
                }
            }
        }
    }
    fun handleAction(action: FilmographyScreenAction) {
       when(action){
           is FilmographyScreenAction.FetchFilmography -> {
               fetchFilmography(action.actor)
           }

           is FilmographyScreenAction.FetchMovies -> {
               getMoviesByFilmographyType(action.actorType)
           }
       }
    }
    private fun fetchFilmography(actor: Actor) {
        _state.value = FilmographyScreenState.Loading
        viewModelScope.launch {
            getActorDetailInfoUseCase(actor.actorId).collect { response ->
                when (response) {
                    is Resource.Error -> {
                        _state.value = FilmographyScreenState.Error(response.message.toString())
                    }

                    is Resource.Success -> {
                        /*actorTypes = actor.allMovies as? Map<ActorType, List<Movie>> ?: emptyMap()*/
                        Log.d("Actor", actor.toString())
                        Log.d("ActorAllMovies", actor.allMovies.toString())
                        Log.d("ActorTypes", actorTypes.toString())
                        Log.d("Profession Keys - first",  actorTypes[actor.professionKeys[0]].toString())
                        Log.d("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxKEye",  actor.professionKeys[0].toString())
                        Log.d("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",  actor.professionKeys.toString())
                        Log.d("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy",  actorTypes[actor.professionKeys[0]].toString())
                        /*_state.value =
                            actorTypes[actor.professionKeys[0]]?.let {
                                FilmographyScreenState.Success(it)
                            } ?: FilmographyScreenState.Error("No movies found")*/
                        actorTypes = response.data.allMovies as? Map<ActorType, List<Movie>> ?: emptyMap()
                        _state.value =
                            actorTypes[actor.professionKeys[0]]?.let {
                                FilmographyScreenState.Success(it)
                            } ?: FilmographyScreenState.Error("No movies found")
                    }
                }
            }
        }
    }

    private fun getMoviesByFilmographyType(actorType: ActorType) {
        val movies = actorTypes[actorType]?.toList() ?: emptyList()
        _state.value = FilmographyScreenState.Success(movies)
    }

 /*   class FilmographyViewModelFactory(
        private val actor: Actor
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FilmographyViewModel::class.java)) {
                return FilmographyViewModel(actor) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }*/
}
