package com.jax.movies.presentation.home.movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.ActorType
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.usecase.movie.AddNewCollectionUseCaseImpl
import com.jax.movies.domain.usecase.movie.CheckIsFavouriteUseCaseImpl
import com.jax.movies.domain.usecase.movie.CheckIsLickedUseCaseImpl
import com.jax.movies.domain.usecase.movie.DeleteFavouriteMovieUseCaseImpl
import com.jax.movies.domain.usecase.movie.DeleteSeenMovieUseCaseImpl
import com.jax.movies.domain.usecase.movie.GetActorsUseCaseImpl
import com.jax.movies.domain.usecase.movie.GetCollectionUseCaseImpl
import com.jax.movies.domain.usecase.movie.GetDetailMovieUseCaseImpl
import com.jax.movies.domain.usecase.movie.GetGalleriesUseCaseImpl
import com.jax.movies.domain.usecase.movie.GetSimilarMoviesUseCaseImpl
import com.jax.movies.domain.usecase.profile.GetFavouriteMoviesUseCaseImpl
import com.jax.movies.domain.usecase.profile.GetSeenMoviesUseCaseImpl
import com.jax.movies.domain.usecase.profile.SaveFavouriteMovieUseCaseImpl
import com.jax.movies.domain.usecase.profile.SaveSeenMovieUseCaseImpl
import com.jax.movies.presentation.components.MovieCollectionItem
import com.jax.movies.utils.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MovieDetailViewModel @AssistedInject constructor(
    @Assisted private val movie: Movie,
    private val getDetailMovieUseCaseImpl: GetDetailMovieUseCaseImpl,
    private val getActorsUseCaseImpl: GetActorsUseCaseImpl,
    private val getGalleriesUseCaseImpl: GetGalleriesUseCaseImpl,
    private val getSimilarMoviesUseCaseImpl: GetSimilarMoviesUseCaseImpl,
    private val saveFavouriteMovieUseCase: SaveFavouriteMovieUseCaseImpl,
    private val saveSeenMovieUseCase: SaveSeenMovieUseCaseImpl,
    private val getMovieCollectionUseCase: GetCollectionUseCaseImpl,
    private val checkIsLickedUseCase: CheckIsLickedUseCaseImpl,
    private val checkIsFavouriteUseCase: CheckIsFavouriteUseCaseImpl,
    private val deleteFavouriteMovieUseCase: DeleteFavouriteMovieUseCaseImpl,
    private val deleteSeenMovieUseCase: DeleteSeenMovieUseCaseImpl,
    private val getSeenMovieUseCase: GetSeenMoviesUseCaseImpl,
    private val getFavouriteMoviesUseCase: GetFavouriteMoviesUseCaseImpl,
    private val onAddNewCollectionUseCase: AddNewCollectionUseCaseImpl,
) : ViewModel() {

    private val _state = MutableStateFlow<MovieDetailState>(MovieDetailState.Initial)
    val state: StateFlow<MovieDetailState> = _state.asStateFlow()

    private val _movieNavigationChannel =
        Channel<MovieScreenIntent.MovieScreenNavigationIntent>(capacity = Channel.CONFLATED)
    val movieNavigationChannel = _movieNavigationChannel.receiveAsFlow()

    fun handleIntent(intent: MovieScreenIntent) {
        when (intent) {
            is MovieScreenIntent.MovieScreenNavigationIntent.OnActorClick -> {
                viewModelScope.launch {
                    _movieNavigationChannel.send(
                        MovieScreenIntent.MovieScreenNavigationIntent.OnActorClick(
                            intent.actor
                        )
                    )
                }
            }

            is MovieScreenIntent.MovieScreenNavigationIntent.OnGalleryClick -> {
                viewModelScope.launch {
                    _movieNavigationChannel.send(
                        MovieScreenIntent.MovieScreenNavigationIntent.OnGalleryClick(
                            intent.movie
                        )
                    )
                }
            }

            is MovieScreenIntent.MovieScreenNavigationIntent.OnMovieClick -> {
                viewModelScope.launch {
                    _movieNavigationChannel.send(
                        MovieScreenIntent.MovieScreenNavigationIntent.OnMovieClick(
                            fromMovie = intent.fromMovie,
                            toMovie = intent.toMovie
                        )
                    )
                }
            }

            is MovieScreenIntent.MovieScreenNavigationIntent.OnBackClicked -> {
                viewModelScope.launch {
                    _movieNavigationChannel.send(
                        MovieScreenIntent.MovieScreenNavigationIntent.OnBackClicked(
                            intent.movie
                        )
                    )
                }
            }

            is MovieScreenIntent.OnFavouriteClick -> {
                viewModelScope.launch {
                    if (intent.isFavourite) {
                        deleteSeenMovieUseCase(intent.movie)
                    } else {
                        saveSeenMovieUseCase(intent.movie)
                    }
                }
            }

            is MovieScreenIntent.OnLickClick -> {
                viewModelScope.launch {
                    if (intent.isLicked) {
                        deleteFavouriteMovieUseCase(intent.movie)
                    } else {
                        saveFavouriteMovieUseCase(intent.movie)
                    }
                }
            }

            is MovieScreenIntent.OnCheck -> {
                viewModelScope.launch {
                    if (intent.movieCollectionItem.name.trim() == "Вам было интересно") {
                        if (intent.isChecked) {
                            Log.d("Вам было интересно", "save")
                            saveSeenMovieUseCase(movie)
                        } else {
                            Log.d("Вам было интересно", "delete")
                            deleteSeenMovieUseCase(movie)
                        }
                    } else if (intent.movieCollectionItem.name.trim() == "Посмотрено") {
                        if (intent.isChecked) saveFavouriteMovieUseCase(intent.movie)
                        else deleteSeenMovieUseCase(intent.movie)
                    }
                }
            }
            is MovieScreenIntent.OnNewCollectionAdd -> {
                viewModelScope.launch {
                    val item = MovieCollectionItem(id = 0, name = intent.name, count = 0)
                    onAddNewCollectionUseCase(item)
                }
            }
            is MovieScreenIntent.OnMoreClick -> {}
            is MovieScreenIntent.OnShareClick -> {}
            is MovieScreenIntent.OnBlindEyeClick -> {}
            is MovieScreenIntent.MovieScreenNavigationIntent.Default -> {}
        }
    }

    fun handleAction(action: MovieScreenAction) {
        when (action) {
            is MovieScreenAction.FetchMovieDetailInfo -> {
                fetchDetailInfo()
            }
        }
    }

    private fun fetchDetailInfo() {
        _state.value = MovieDetailState.Loading
        val movieDeferred: Deferred<Movie> = viewModelScope.async {
            var finalMovie = movie
            Log.d("dsdasdasdasdasssssssssssssssssssssssssssssssssssssssss", movie.id.toString())
            getDetailMovieUseCaseImpl(movie.id).first { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = MovieDetailState.Error(result.message.toString())
                        return@first true
                    }

                    is Resource.Success -> {
                        finalMovie = result.data
                        finalMovie = finalMovie.copy(id = movie.id)
                        return@first true
                    }
                }
            }
            Log.d("dsadsadas", finalMovie.toString())
            finalMovie
        }

        val actorsDeferred: Deferred<List<Actor>> = viewModelScope.async {
            var actorsList: List<Actor> = emptyList()
            getActorsUseCaseImpl(movie.id).first { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = MovieDetailState.Error(result.message.toString())
                        return@first true
                    }

                    is Resource.Success -> {
                        actorsList = result.data
                        return@first true
                    }
                }
            }
            Log.d("dsadsadas", actorsList.toString())
            actorsList
        }
        val galleriesDeferred = viewModelScope.async {
            var galleriesList: List<GalleryImage> = emptyList()
            getGalleriesUseCaseImpl(movie.id).first { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = MovieDetailState.Error(result.message.toString())
                        return@first true
                    }

                    is Resource.Success -> {
                        galleriesList = result.data
                        return@first true
                    }
                }
            }
            Log.d("dsadsadas", galleriesList.toString())
            galleriesList
        }
        val similarMoviesDeferred = viewModelScope.async {
            var similarMoviesList: List<Movie> = emptyList()
            getSimilarMoviesUseCaseImpl(movie.id).first { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = MovieDetailState.Error(result.message.toString())
                        return@first true
                    }

                    is Resource.Success -> {
                        similarMoviesList = result.data
                        return@first true
                    }
                }
            }
            Log.d("dsadsadas", similarMoviesList.toString())
            similarMoviesList
        }
        val collection = viewModelScope.async {
            var movieCollection: List<MovieCollectionItem> = emptyList()
            getMovieCollectionUseCase().first { response ->
                movieCollection = response
                return@first true
            }
            Log.d("dsadsadas", movieCollection.toString())
            movieCollection
        }

        val isLicked = viewModelScope.async {
            var isLicked = false
            checkIsLickedUseCase(movie.id).first { response ->
                isLicked = response
                return@first true
            }
            isLicked
        }
        val isFavourite = viewModelScope.async {
            var isFavourite = false
            checkIsFavouriteUseCase(movie.id).first { response ->
                isFavourite = response
                return@first true
            }
            isFavourite
        }
        viewModelScope.launch {
            val finalMovie = movieDeferred.await()
            val actorsList = actorsDeferred.await()
            val galleriesList = galleriesDeferred.await()
            val similarMoviesList = similarMoviesDeferred.await()
            val filmCrew = getFilmCrew(actorsList)
            val collections = collection.await()
            val licked: Boolean = isLicked.await()
            val favourite: Boolean = isFavourite.await()
            val favItemCount = getFavouriteMoviesUseCase().first().size
            val seenItemCount = getSeenMovieUseCase().first().size
            _state.value = MovieDetailState.Success(
                movie = finalMovie,
                actors = actorsList,
                filmCrew = filmCrew,
                gallery = galleriesList,
                similarMovies = similarMoviesList,
                collection = collections,
                isLicked = licked,
                isFavourite = favourite,
                favouriteMovieItemCount = favItemCount,
                seenMovieItemCount = seenItemCount
            )
        }
    }

    private fun getFilmCrew(actors: List<Actor>): List<Actor> {
        return actors.filter { actor ->
            when (actor.profession) {
                ActorType.DIRECTOR.name,
                ActorType.WRITE.name,
                ActorType.PRODUCER.name,
                ActorType.OPERATOR.name,
                ActorType.DESIGN.name,
                ActorType.VOICE_DIRECTOR.name,
                ActorType.PRODUCER_USSR.name -> true

                else -> false
            }
        }
    }

    @AssistedFactory
    interface MovieDetailFactory {
        fun create(movie: Movie): MovieDetailViewModel
    }

    companion object {
        fun provideMovieDetailFactory(
            movie: Movie,
            factory: MovieDetailFactory
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(movie) as T
            }
        }
    }
}















