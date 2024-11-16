package com.jax.movies.presentation.detail.actor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.usecase.GetActorDetailInfoUseCaseImpl
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ActorDetailViewModel : ViewModel() {

    private val getActorDetailInfoUseCase = GetActorDetailInfoUseCaseImpl()

    private val _state = MutableStateFlow<ActorDetailState>(ActorDetailState.Initial)
    val state: StateFlow<ActorDetailState> = _state.asStateFlow()
    fun fetchDetailInfo(actor: Actor) {
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
}