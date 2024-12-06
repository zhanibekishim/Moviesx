package com.jax.movies.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.usecase.onboarding.GetIsEnteredBeforeValueUseCaseImpl
import com.jax.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val getIsEnteredBeforeValueUseCase: GetIsEnteredBeforeValueUseCaseImpl
) : ViewModel() {
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


