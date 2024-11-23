package com.jax.movies.presentation.onboarding

sealed class OnBoardingScreenState {
    data object Initial : OnBoardingScreenState()
    data class Success(val value: Boolean) : OnBoardingScreenState()
    data class Error(val message: String) : OnBoardingScreenState()
}