package com.jax.movies.presentation.search.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.usecase.search.GetGenresUseCaseImpl
import com.jax.movies.presentation.search.country.CountryScreenState
import com.jax.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCaseImpl
) : ViewModel() {

    private val _screenState = MutableStateFlow<GenreScreenState>(GenreScreenState.Initial)
    val screenState = _screenState.asStateFlow()

    private val _genresNavigation = Channel<GenreScreenIntent.Event>()
    val genresNavigation = _genresNavigation.receiveAsFlow()

    fun handleIntent(intent: GenreScreenIntent) {
        when (intent) {
            is GenreScreenIntent.OnQueryChange -> {
               /* viewModelScope.launch {
                    _screenState.value = GenreScreenState.Success(query = intent.query)
                }*/
            }

            is GenreScreenIntent.OnSearchClick -> {
                _screenState.value = GenreScreenState.Loading(query = intent.query)
                viewModelScope.launch {
                    getGenresUseCase(intent.query).collect { response ->
                        when (response) {
                            is Resource.Error -> {
                                _screenState.value = GenreScreenState.Error(
                                    error = response.message.toString(),
                                    query = intent.query
                                )
                            }

                            is Resource.Success -> {
                                _screenState.value = GenreScreenState.Success(
                                    genres = response.data,
                                    query = intent.query
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun handleEvent(event: GenreScreenIntent.Event) {
        when (event) {
            GenreScreenIntent.Event.OnClickBack -> {
                viewModelScope.launch {
                    _genresNavigation.send(GenreScreenIntent.Event.OnClickBack)
                }
            }

            is GenreScreenIntent.Event.OnGenreClick -> {
                viewModelScope.launch {
                    _genresNavigation.send(GenreScreenIntent.Event.OnGenreClick(event.genre))
                }
            }

            GenreScreenIntent.Event.Default -> {}
        }
    }
}











