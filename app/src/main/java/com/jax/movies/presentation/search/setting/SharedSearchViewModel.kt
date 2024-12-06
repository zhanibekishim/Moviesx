package com.jax.movies.presentation.search.setting

import com.jax.movies.domain.entity.home.Country
import com.jax.movies.domain.entity.home.Genre


object SharedSearchViewModel  {
    var selectedCountry: Country? = null
        private set
    var selectedGenre: Genre? = null
        private set
    var selectedPeriod: String? = null
        private set
    var selectedRating: Double? = null
        private set
    var selectedShowType: ShowType? = null
        private set
    var selectedSortType: SortingType? = null
        private set
    fun setCountry(country: Country) {
        selectedCountry = country
    }

    fun setGenre(genre: Genre) {
        selectedGenre = genre
    }

    fun setPeriod(period: String) {
        selectedPeriod = period
    }
    fun setRating(rating: Double) {
        selectedRating = rating
    }
    fun setShowType(showType: ShowType) {
        selectedShowType = showType
    }
    fun setSortType(sortType: SortingType) {
        selectedSortType = sortType
    }
}
