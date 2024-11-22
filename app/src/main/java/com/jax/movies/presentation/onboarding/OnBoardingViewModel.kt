package com.jax.movies.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.usecase.GetIsEnteredBeforeValueUseCaseImpl
import com.jax.movies.utils.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class OnBoardingViewModel : ViewModel() {

    private val getIsEnteredBeforeValueUseCase = GetIsEnteredBeforeValueUseCaseImpl()
    private val _onBoardingNavigationChannel = Channel<OnBoardingScreenIntent>()
    val onBoardingNavigationChannel = _onBoardingNavigationChannel.receiveAsFlow()

    private val _screenState =
        MutableStateFlow<OnBoardingScreenState>(OnBoardingScreenState.Initial)
    val screenState = _screenState.asStateFlow()

    fun handleIntent(intent: OnBoardingScreenIntent) {
        when (intent) {
            is OnBoardingScreenIntent.OnFinishClicked -> {
                viewModelScope.launch {
                    _onBoardingNavigationChannel.send(intent)
                }
            }

            OnBoardingScreenIntent.Default -> {}
        }
    }

    fun handleAction(action: OnBoardingScreenAction) {
        when (action) {
            OnBoardingScreenAction.GetIsEnteredBeforeAction -> {
                viewModelScope.launch {
                    getIsEnteredBeforeValueUseCase().collect { response ->
                        _screenState.value = when (response) {
                            is Resource.Error -> OnBoardingScreenState.Error(response.message.toString())
                            is Resource.Success -> OnBoardingScreenState.Success(response.data)
                        }
                    }
                }
            }
        }
    }
}

sealed class OnBoardingScreenState {
    data object Initial : OnBoardingScreenState()
    data class Success(val value: Boolean) : OnBoardingScreenState()
    data class Error(val message: String) : OnBoardingScreenState()
}

