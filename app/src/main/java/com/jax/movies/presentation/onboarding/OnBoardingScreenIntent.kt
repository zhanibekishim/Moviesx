package com.jax.movies.presentation.onboarding

sealed class OnBoardingScreenIntent {
    data object Default : OnBoardingScreenIntent()
    data object OnFinishClicked : OnBoardingScreenIntent()
}