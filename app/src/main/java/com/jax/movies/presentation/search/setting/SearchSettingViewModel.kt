package com.jax.movies.presentation.search.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchSettingViewModel @Inject constructor() : ViewModel() {
    private val sharedPreferencesViewModel = SharedSearchViewModel
    private val _screenState = MutableStateFlow(SearchSettingScreenState())
    val screenState = _screenState.asStateFlow()

    private val _navigationChannel = Channel<SearchSettingScreenIntent.Event>()
    val navigationChannel = _navigationChannel.receiveAsFlow()

    fun handleEvent(event: SearchSettingScreenIntent.Event) {
        when (event) {
            SearchSettingScreenIntent.Event.OnBackClick -> {
                viewModelScope.launch {
                    _navigationChannel.send(SearchSettingScreenIntent.Event.OnBackClick)
                    delay(100)
                    _navigationChannel.send(SearchSettingScreenIntent.Event.Default)
                }
            }

            SearchSettingScreenIntent.Event.OnCountryClick -> {
                viewModelScope.launch {
                    _navigationChannel.send(SearchSettingScreenIntent.Event.OnCountryClick)
                    delay(100)
                    _navigationChannel.send(SearchSettingScreenIntent.Event.Default)
                }
            }

            SearchSettingScreenIntent.Event.OnGenreClick -> {
                viewModelScope.launch {
                    _navigationChannel.send(SearchSettingScreenIntent.Event.OnGenreClick)
                    delay(100)
                    _navigationChannel.send(SearchSettingScreenIntent.Event.Default)
                }
            }

            SearchSettingScreenIntent.Event.OnPeriodClick -> {
                viewModelScope.launch {
                    _navigationChannel.send(SearchSettingScreenIntent.Event.OnPeriodClick)
                    delay(100)
                    _navigationChannel.send(SearchSettingScreenIntent.Event.Default)
                }
            }

            SearchSettingScreenIntent.Event.Default -> {}
        }
    }

    fun handleIntent(intent: SearchSettingScreenIntent) {
        when (intent) {
            is SearchSettingScreenIntent.OnFilterTypeChoose -> {
                _screenState.update {
                    it.copy(filterType = intent.filterType)
                }
            }

            is SearchSettingScreenIntent.OnRatingChange -> {
                _screenState.update {
                    it.copy(rating = intent.rating)
                }
                sharedPreferencesViewModel.setRating(intent.rating)
            }

            is SearchSettingScreenIntent.OnShowTypeChoose -> {
                _screenState.update {
                    it.copy(showType = intent.showType)
                }
                sharedPreferencesViewModel.setShowType(intent.showType)
            }

            is SearchSettingScreenIntent.OnSortingTypeChoose -> {
                _screenState.update {
                    it.copy(sortingType = intent.sortingType)
                }
                sharedPreferencesViewModel.setSortType(intent.sortingType)
            }

            is SearchSettingScreenIntent.OnCountryChange -> {
                viewModelScope.launch {
                    Log.d("handleIntent", "Updating country to ${intent.country}")
                    _screenState.update {
                        it.copy(
                            country = intent.country.name,
                            filterType = FilterType.Country(intent.country.name)
                        )
                    }
                }

            }

            is SearchSettingScreenIntent.OnGenreChange -> {
                viewModelScope.launch {
                    Log.d("handleIntent", "Updating genre to ${intent.genre}")
                    _screenState.update {
                        it.copy(
                            genre = intent.genre.name,
                            filterType = FilterType.Genre(intent.genre.name)
                        )
                    }
                }
            }

            is SearchSettingScreenIntent.OnPeriodChange -> {
                viewModelScope.launch {
                    Log.d("handleIntent", "Updating period to ${intent.periodFrom}")
                    _screenState.update {
                        it.copy(
                            periodTo = intent.periodTo, periodFrom = intent.periodFrom,
                            filterType = FilterType.Period(
                                to = intent.periodTo,
                                from = intent.periodFrom
                            )
                        )
                    }
                }
            }
        }
    }
}
