package com.jax.movies.presentation.search.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchSettingViewModel @Inject constructor() : ViewModel() {

    private val _screenState = MutableStateFlow(SearchSettingScreenState())
    val screenState = _screenState.asStateFlow()

    private val _navigationChannel = Channel<SearchSettingScreenIntent.Event>()
    val navigationChannel = _navigationChannel.receiveAsFlow()

    fun handleEvent(event: SearchSettingScreenIntent.Event) {
        when (event) {
            SearchSettingScreenIntent.Event.OnBackClick -> {
                viewModelScope.launch {
                    _navigationChannel.send(SearchSettingScreenIntent.Event.OnBackClick)
                }
            }

            SearchSettingScreenIntent.Event.OnCountryClick -> {
                viewModelScope.launch {
                    _navigationChannel.send(SearchSettingScreenIntent.Event.OnCountryClick)
                }
            }

            SearchSettingScreenIntent.Event.OnGenreClick -> {
                viewModelScope.launch {
                    _navigationChannel.send(SearchSettingScreenIntent.Event.OnGenreClick)
                }
            }

            SearchSettingScreenIntent.Event.OnPeriodClick -> {
                viewModelScope.launch {
                    _navigationChannel.send(SearchSettingScreenIntent.Event.OnPeriodClick)
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
            }

            is SearchSettingScreenIntent.OnShowTypeChoose -> {
                _screenState.update {
                    it.copy(showType = intent.showType)
                }
            }

            is SearchSettingScreenIntent.OnSortingTypeChoose -> {
                _screenState.update {
                    it.copy(sortingType = intent.sortingType)
                }
            }

            is SearchSettingScreenIntent.OnCountryChange -> {
                _screenState.update {
                    it.copy(country = intent.country)
                }
            }
            is SearchSettingScreenIntent.OnGenreChange -> {
                _screenState.update {
                    it.copy(genre = intent.genre)
                }
            }
            is SearchSettingScreenIntent.OnPeriodChange -> {
                _screenState.update {
                    it.copy(period = intent.period)
                }
            }
        }
    }
}
