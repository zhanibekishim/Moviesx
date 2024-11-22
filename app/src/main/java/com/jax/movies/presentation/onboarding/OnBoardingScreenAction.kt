package com.jax.movies.presentation.onboarding

sealed class OnBoardingScreenAction {
    data object GetIsEnteredBeforeAction:OnBoardingScreenAction()
}