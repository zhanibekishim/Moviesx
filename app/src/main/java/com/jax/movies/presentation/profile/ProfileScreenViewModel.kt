package com.jax.movies.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.usecase.profile.GetFavouriteMoviesUseCaseImpl
import com.jax.movies.domain.usecase.profile.GetSeenMoviesUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getFavouriteMoviesUseCase: GetFavouriteMoviesUseCaseImpl,
    private val getSeenMoviesUseCase: GetSeenMoviesUseCaseImpl
) : ViewModel() {

    private val _favouriteMovies = getFavouriteMoviesUseCase()
    private val _seenMovies = getSeenMoviesUseCase()

    private val _state = combine(_favouriteMovies, _seenMovies) { favouriteMovies, seenMovies ->
        ProfileScreenState.Success(favouriteMovies, seenMovies)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ProfileScreenState.Initial)

    val state = _state.asLiveData()

    private val _profileNavigationChannel = Channel<ProfileScreenIntent>()
    val profileNavigationChannel= _profileNavigationChannel.receiveAsFlow()
    fun handleIntent(intent:ProfileScreenIntent){
        when(intent){
            is ProfileScreenIntent.Default -> {}
            ProfileScreenIntent.OnHomeClick -> {
                viewModelScope.launch {
                    _profileNavigationChannel.send(ProfileScreenIntent.OnHomeClick)
                }
            }
            is ProfileScreenIntent.OnSearchClick -> {
                viewModelScope.launch {
                    _profileNavigationChannel.send(ProfileScreenIntent.OnSearchClick)
                }
            }

            is ProfileScreenIntent.OnMovieClick -> {
                viewModelScope.launch {
                    _profileNavigationChannel.send(ProfileScreenIntent.OnMovieClick(intent.movie))
                }
            }
        }
    }
}













