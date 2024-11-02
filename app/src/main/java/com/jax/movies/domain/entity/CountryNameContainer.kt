package com.jax.movies.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryNameContainer(
  val country: String
):Parcelable