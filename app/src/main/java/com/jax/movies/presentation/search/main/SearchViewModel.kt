package com.jax.movies.presentation.search.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.usecase.search.SearchQueryUseCaseImpl
import com.jax.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
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

    private val _queryFlow = MutableStateFlow("")
    private val _sortByFlow = MutableStateFlow<SortBy?>(null)

    init {
        observeSearchQuery()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _queryFlow
                .debounce(2000)
                .distinctUntilChanged()
                .combine(_sortByFlow) { query, sortBy -> Pair(query, sortBy) }
                .collectLatest { (query, sortBy) ->
                    if (query.isNotBlank()) {
                        _screenState.value = SearchScreenState.Loading(query)
                        searchQueryUseCase(query, sortBy?:SortBy())
                            .collectLatest { resource ->
                                when (resource) {
                                    is Resource.Error -> {
                                        _screenState.value = SearchScreenState.Error(
                                            error = resource.message.toString(),
                                            query = query
                                        )
                                    }

                                    is Resource.Success -> {
                                        _screenState.value = SearchScreenState.Success(
                                            movies = resource.data,
                                            query = query
                                        )
                                    }
                                }
                            }
                    }
                }
        }
    }

    fun handleIntent(intent: SearchScreenIntent) {
        when (intent) {
            is SearchScreenIntent.OnValueChange -> {
                _queryFlow.value = intent.query
                _sortByFlow.value = intent.sortBy
            }
        }
    }

    fun handleEvent(event: SearchScreenIntent.Event) {
        viewModelScope.launch {
            when (event) {
                SearchScreenIntent.Event.OnFilterClick -> {
                    _navigationChannel.send(SearchScreenIntent.Event.OnFilterClick)
                }
                SearchScreenIntent.Event.OnHomeClick -> {
                    _navigationChannel.send(SearchScreenIntent.Event.OnHomeClick)
                }
                SearchScreenIntent.Event.OnProfileClick -> {
                    _navigationChannel.send(SearchScreenIntent.Event.OnProfileClick)
                }
                is SearchScreenIntent.Event.OnMovieClick -> {
                    _navigationChannel.send(SearchScreenIntent.Event.OnMovieClick(event.movie))
                }
                SearchScreenIntent.Event.Default -> {}
            }
            delay(100)
            _navigationChannel.send(SearchScreenIntent.Event.Default)
        }
    }
}