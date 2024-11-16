package com.jax.movies.presentation.detail.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.usecase.GetGalleriesUseCaseImpl
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GalleryViewModel : ViewModel() {

    private val getGalleriesUseCaseImpl = GetGalleriesUseCaseImpl()
    private val _state = MutableStateFlow<GalleryScreenState>(GalleryScreenState.Initial)
    val state: StateFlow<GalleryScreenState> = _state.asStateFlow()

    fun fetchGalleries(filmId: Long){
        _state.value = GalleryScreenState.Loading
        viewModelScope.launch {
            getGalleriesUseCaseImpl(filmId).collect{response->
                when(response){
                    is Resource.Error -> _state.value = GalleryScreenState.Error(response.message.toString())
                    is Resource.Success -> _state.value = GalleryScreenState.Success(response.data)
                }
            }
        }
    }
}