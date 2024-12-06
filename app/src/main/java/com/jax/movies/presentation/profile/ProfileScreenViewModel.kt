package com.jax.movies.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.usecase.movie.AddNewCollectionUseCase
import com.jax.movies.domain.usecase.movie.AddNewCollectionUseCaseImpl
import com.jax.movies.domain.usecase.movie.GetCollectionUseCaseImpl
import com.jax.movies.domain.usecase.profile.DeleteAllMoviesCollectionUseCaseImpl
import com.jax.movies.domain.usecase.profile.DeleteCollectionUseCaseImpl
import com.jax.movies.domain.usecase.profile.DeleteFavouriteMoviesUseCaseImpl
import com.jax.movies.domain.usecase.profile.DeleteSeenMoviesUseCaseImpl
import com.jax.movies.domain.usecase.profile.GetFavouriteMoviesUseCaseImpl
import com.jax.movies.domain.usecase.profile.GetSeenMoviesUseCaseImpl
import com.jax.movies.presentation.components.MovieCollectionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getFavouriteMoviesUseCase: GetFavouriteMoviesUseCaseImpl,
    private val getSeenMoviesUseCase: GetSeenMoviesUseCaseImpl,
    private val getCollectionUseCase: GetCollectionUseCaseImpl,
    private val addNewCollectionUseCase: AddNewCollectionUseCaseImpl,
    private val deleteFavouriteMoviesUseCase: DeleteFavouriteMoviesUseCaseImpl,
    private val deleteSeenMoviesUseCase: DeleteSeenMoviesUseCaseImpl,
    private val onAddNewCollectionUseCase: AddNewCollectionUseCaseImpl,
    private val deleteCollectionUseCase: DeleteCollectionUseCaseImpl,
    private val deleteAllMoviesCollectionUseCase: DeleteAllMoviesCollectionUseCaseImpl,
) : ViewModel() {

    private val _favouriteMovies = getFavouriteMoviesUseCase()
    private val _seenMovies = getSeenMoviesUseCase()
    private val _collection = getCollectionUseCase()

   /* init { update() }*/

    private val _state = combine(
        _favouriteMovies,
        _seenMovies,
        _collection
    ) { favouriteMovies, seenMovies, collection ->
        ProfileScreenState.Success(favouriteMovies, seenMovies, collection)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ProfileScreenState.Initial)

    val state = _state.asLiveData()

    private val _profileNavigationChannel = Channel<ProfileScreenIntent.Event>()
    val profileNavigationChannel = _profileNavigationChannel.receiveAsFlow()
    fun handleEvent(event: ProfileScreenIntent.Event) {
        when (event) {
            is ProfileScreenIntent.Event.Default -> {}
            ProfileScreenIntent.Event.OnHomeClick -> {
                viewModelScope.launch {
                    _profileNavigationChannel.send(ProfileScreenIntent.Event.OnHomeClick)
                }
            }

            is ProfileScreenIntent.Event.OnSearchClick -> {
                viewModelScope.launch {
                    _profileNavigationChannel.send(ProfileScreenIntent.Event.OnSearchClick)
                }
            }

            is ProfileScreenIntent.Event.OnMovieClick -> {
                viewModelScope.launch {
                    _profileNavigationChannel.send(ProfileScreenIntent.Event.OnMovieClick(event.movie))
                }
            }

            is ProfileScreenIntent.Event.OnDeleteClick -> {
                viewModelScope.launch {
                    when (event.name) {
                        "Посмотрено" -> deleteSeenMoviesUseCase()
                        "Вам было интересно" -> deleteFavouriteMoviesUseCase()
                    }
                }
            }
        }
    }
    fun handleIntent(intent: ProfileScreenIntent){
        when(intent){
            is ProfileScreenIntent.OnAddNewCollection -> {
                viewModelScope.launch {
                    val item = MovieCollectionItem(id = 0, name = intent.name, count = 0)
                    onAddNewCollectionUseCase(item)
                }
            }

            is ProfileScreenIntent.OnDeleteCollection -> {
                viewModelScope.launch {
                    deleteCollectionUseCase(intent.movieCollectionItem)
                }
            }
        }
    }
    private fun update(){
        viewModelScope.launch {
            addNewCollectionUseCase(MovieCollectionItem(name = "Посмотрено", count = 0))
            addNewCollectionUseCase(MovieCollectionItem(name = "Вам было интересно", count = 0))
            getCollectionUseCase().collect { response ->
                response.forEach { collection ->
                    addNewCollectionUseCase(collection)
                }
            }
         /*   val _favouriteMovies = getFavouriteMoviesUseCase().first()
            val _seenMovies = getSeenMoviesUseCase().first()
            val _collection = getCollectionUseCase().first()
            _state.value = ProfileScreenState.Success(favouriteMovies = _favouriteMovies,seenMovies = _seenMovies,collection = _collection)*/
        }
    }
}













