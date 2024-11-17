package com.jax.movies.presentation.detail.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.usecase.GetGalleriesUseCaseImpl
import com.jax.movies.utils.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class GalleryViewModel : ViewModel() {

    private val getGalleriesUseCaseImpl = GetGalleriesUseCaseImpl()
    private val _state = MutableStateFlow<GalleryScreenState>(GalleryScreenState.Initial)
    val state: StateFlow<GalleryScreenState> = _state.asStateFlow()

    private val _galleryNavigationChannel = Channel<GalleryScreenIntent>()
    val galleryNavigationChannel = _galleryNavigationChannel.receiveAsFlow()

    fun handleIntent(intent: GalleryScreenIntent) {
        when(intent){
            GalleryScreenIntent.OnClickBack -> {
                viewModelScope.launch {
                    _galleryNavigationChannel.send(GalleryScreenIntent.OnClickBack)
                }
            }
            GalleryScreenIntent.Default -> {}
        }
    }

    fun handleAction(action: GalleryScreenAction){
        when(action){
            is GalleryScreenAction.FetchGalleries -> {
                fetchGalleries(action.movie)
            }
        }

    }
    private fun fetchGalleries(movie: Movie){
        _state.value = GalleryScreenState.Loading
        viewModelScope.launch {
            getGalleriesUseCaseImpl(movie.id).collect{response->
                when(response){
                    is Resource.Error -> _state.value = GalleryScreenState.Error(response.message.toString())
                    is Resource.Success -> _state.value = GalleryScreenState.Success(response.data)
                }
            }
        }
    }
}