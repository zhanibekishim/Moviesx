package com.jax.movies.presentation.home.actor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.usecase.GetActorDetailInfoUseCaseImpl
import com.jax.movies.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class ActorDetailViewModel @AssistedInject constructor(
    @Assisted private val actor: Actor,
    private val getActorDetailInfoUseCase: GetActorDetailInfoUseCaseImpl
) : ViewModel() {

    private val _state = MutableStateFlow<ActorDetailState>(ActorDetailState.Initial)
    val state: StateFlow<ActorDetailState> = _state.asStateFlow()

    private val _actorNavigationChannel = Channel<ActorScreenIntent>()
    val actorNavigationChannel = _actorNavigationChannel.receiveAsFlow()

    fun handleIntent(intent: ActorScreenIntent) {
        when (intent) {
            is ActorScreenIntent.Default -> {}
            is ActorScreenIntent.OnFilmographyClick -> {
                viewModelScope.launch {
                    _actorNavigationChannel.send(ActorScreenIntent.OnFilmographyClick(intent.actor))
                }
            }

            is ActorScreenIntent.OnMovieClick -> {
                viewModelScope.launch {
                    _actorNavigationChannel.send(
                        ActorScreenIntent.OnMovieClick(
                            intent.movie,
                            intent.actor
                        )
                    )
                }
            }

            ActorScreenIntent.OnClickBack -> {
                viewModelScope.launch {
                    _actorNavigationChannel.send(ActorScreenIntent.OnClickBack)
                }
            }
        }
    }

    fun handleAction(action: ActorScreenAction) {
        when (action) {
            is ActorScreenAction.FetchActorDetailInfo -> fetchDetailInfo(action.actor)
        }
    }

    private fun fetchDetailInfo(actor: Actor) {
        _state.value = ActorDetailState.Loading
        viewModelScope.launch {
            getActorDetailInfoUseCase(actor.actorId).collectLatest { response ->
                when (response) {
                    is Resource.Error -> _state.value =
                        ActorDetailState.Error(response.message.toString())

                    is Resource.Success -> {
                        Log.d("qqqqqqqqqqqqqqqqqqqqqqqqqqq", "Success: ${response.data}")
                        _state.value = ActorDetailState.Success(response.data)
                    }
                }
            }
        }
    }
    @AssistedFactory
    interface ActorDetailViewModelFactory{
        fun create(actor: Actor):ActorDetailViewModel
    }
    companion object{
        fun provideActorDetailViewModelFactory(
            actor: Actor,
            factory: ActorDetailViewModelFactory
        ):ViewModelProvider.Factory = object:ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(actor) as T
            }
        }
    }
}
















