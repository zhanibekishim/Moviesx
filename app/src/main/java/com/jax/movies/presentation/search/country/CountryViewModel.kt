package com.jax.movies.presentation.search.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.usecase.search.GetCountriesUseCaseImpl
import com.jax.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCaseImpl
) : ViewModel() {

    private val _screenState = MutableStateFlow<CountryScreenState>(CountryScreenState.Initial)
    val screenState = _screenState.asStateFlow()

    private val _countryNavigation = Channel<CountryScreenIntent.Event>()
    val countryNavigation = _countryNavigation.receiveAsFlow()

    fun handleIntent(intent: CountryScreenIntent) {
        when (intent) {
            is CountryScreenIntent.OnQueryChange -> {
                viewModelScope.launch {
                    _screenState.value = CountryScreenState.Success(query = intent.query)
                }
            }

            is CountryScreenIntent.OnSearchClick -> {
                _screenState.value = CountryScreenState.Loading(query = intent.query)
                viewModelScope.launch {
                    getCountriesUseCase(intent.query).collect { response ->
                        when (response) {
                            is Resource.Error -> {
                                _screenState.value = CountryScreenState.Error(
                                    error = response.message.toString(),
                                    query = intent.query
                                )
                            }

                            is Resource.Success -> {
                                _screenState.value = CountryScreenState.Success(
                                    countries = response.data,
                                    query = intent.query
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun handleEvent(event: CountryScreenIntent.Event) {
        when (event) {
            CountryScreenIntent.Event.Default -> {}
            CountryScreenIntent.Event.OnClickBack -> {
                viewModelScope.launch {
                    _countryNavigation.send(CountryScreenIntent.Event.OnClickBack)
                }
            }

            is CountryScreenIntent.Event.OnCountryClick -> {
                viewModelScope.launch {
                    _countryNavigation.send(CountryScreenIntent.Event.OnCountryClick(event.country))
                }
            }
        }
    }
}