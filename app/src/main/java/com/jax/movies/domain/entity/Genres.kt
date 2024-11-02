package com.jax.movies.domain.entity

import androidx.compose.runtime.Immutable

@Immutable
data class Genres(
    val genres: List<GenreNameContainer>
)