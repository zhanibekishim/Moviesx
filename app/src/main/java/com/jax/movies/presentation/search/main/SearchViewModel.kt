package com.jax.movies.presentation.search.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.usecase.search.SearchQueryUseCaseImpl
import com.jax.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchQueryUseCase: SearchQueryUseCaseImpl
) : ViewModel() {

    private val _screenState = MutableStateFlow<SearchScreenState>(SearchScreenState.Initial)
    val screenState = _screenState.asStateFlow()

    private val _navigationChannel = Channel<SearchScreenIntent.Event>()
    val navigationChannel = _navigationChannel.receiveAsFlow()

    fun handleIntent(intent: SearchScreenIntent) {
        when (intent) {
            is SearchScreenIntent.OnValueChange -> {
                viewModelScope.launch {
                    _screenState.value = SearchScreenState.Loading
                    searchQueryUseCase(intent.value).collect {resource->
                        when(resource){
                            is Resource.Error -> {
                                _screenState.value =
                                    SearchScreenState.Error(resource.message.toString())
                            }
                            is Resource.Success -> {
                                _screenState.value = SearchScreenState.Success(resource.data)
                            }
                        }
                    }
                }
            }
        }
    }

    fun handleEvent(event: SearchScreenIntent.Event) {
        when (event) {
            SearchScreenIntent.Event.OnFilterClick -> {
                viewModelScope.launch {
                    _navigationChannel.send(SearchScreenIntent.Event.OnFilterClick)
                }
            }

            SearchScreenIntent.Event.OnHomeClick ->{
                viewModelScope.launch {
                    _navigationChannel.send(SearchScreenIntent.Event.OnHomeClick)
                }
            }
            SearchScreenIntent.Event.OnProfileClick -> {
                viewModelScope.launch {
                    _navigationChannel.send(SearchScreenIntent.Event.OnProfileClick)
                }
            }

            is SearchScreenIntent.Event.OnMovieClick -> {
                viewModelScope.launch {
                    _navigationChannel.send(SearchScreenIntent.Event.OnMovieClick(event.movie))
                }
            }

            SearchScreenIntent.Event.Default -> {}
        }
    }
}