package com.jax.movies.presentation.search.period

import androidx.compose.runtime.Composable
import com.jax.movies.presentation.components.PeriodSection

@Composable
fun PeriodScreen(
    onClickBack:()->Unit,
    onChoosePeriod:(String,String)->Unit
) {
    PeriodSection(
        onPeriodChooseClick = onChoosePeriod,
        onClickBack = onClickBack
    )
}