package com.jax.movies.domain.entity.home

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
@Immutable
@Parcelize
data class CountryNameContainer(
  val country: String
):Parcelable