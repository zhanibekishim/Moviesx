package com.jax.movies.domain.entity

import androidx.compose.runtime.Immutable

@Immutable
data class Countries(
    val countries: List<CountryNameContainer>
)