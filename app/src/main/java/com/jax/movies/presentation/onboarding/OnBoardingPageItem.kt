package com.jax.movies.presentation.onboarding

import androidx.annotation.DrawableRes
import com.jax.movies.R

data class OnBoardingPageItem(
    val title: String,
    @DrawableRes val imageId: Int,
){
   companion object{
       val onboardingPages = listOf(
           OnBoardingPageItem(
               imageId = R.drawable.wfh_1,
               title = "Узнавай\nо премьерах",
           ),
           OnBoardingPageItem(
               imageId = R.drawable.wfh_2,
               title = "Создавай\nколлекции",
           ),
           OnBoardingPageItem(
               imageId = R.drawable.wfh_3,
               title = "Делись\nс друзьями",
           )
       )
   }
}