package com.jax.movies.presentation.detail.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.usecase.GetGalleriesUseCaseImpl
import com.jax.movies.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class GalleryViewModel @AssistedInject constructor(
    @Assisted private val movie: Movie,
    private val getGalleriesUseCaseImpl: GetGalleriesUseCaseImpl
) : ViewModel() {

    private val _state = MutableStateFlow<GalleryScreenState>(GalleryScreenState.Initial)
    val state: StateFlow<GalleryScreenState> = _state.asStateFlow()

    private val _galleryNavigationChannel = Channel<GalleryScreenIntent>()
    val galleryNavigationChannel = _galleryNavigationChannel.receiveAsFlow()

    fun handleIntent(intent: GalleryScreenIntent) {
        when (intent) {
            GalleryScreenIntent.OnClickBack -> {
                viewModelScope.launch {
                    _galleryNavigationChannel.send(GalleryScreenIntent.OnClickBack)
                }
            }

            GalleryScreenIntent.Default -> {}
        }
    }

    fun handleAction(action: GalleryScreenAction) {
        when (action) {
            is GalleryScreenAction.FetchGalleries -> {
                fetchGalleries(action.movie)
            }
        }

    }

    private fun fetchGalleries(movie: Movie) {
        _state.value = GalleryScreenState.Loading
        viewModelScope.launch {
            getGalleriesUseCaseImpl(movie.id).collect { response ->
                when (response) {
                    is Resource.Error -> _state.value =
                        GalleryScreenState.Error(response.message.toString())

                    is Resource.Success -> _state.value = GalleryScreenState.Success(response.data)
                }
            }
        }
    }

    @AssistedFactory
    interface GalleryViewModelFactory{
        fun create(movie: Movie):GalleryViewModel
    }

    companion object{
        fun provideGalleryViewModel(
            movie: Movie,
            factory: GalleryViewModelFactory,
        ):ViewModelProvider.Factory = object:ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(movie) as T
            }
        }
    }
}
















