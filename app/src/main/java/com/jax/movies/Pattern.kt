
package com.jax.movies
/*
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.Upsert
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestState
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.jax.movies.data.local.database.Converters
import com.jax.movies.data.local.database.FavouriteMoviesDao
import com.jax.movies.data.local.database.MovieDatabase
import com.jax.movies.data.local.database.SeenMoviesDao
import com.jax.movies.data.local.model.FavouriteMovie
import com.jax.movies.data.local.model.SeenMovie
import com.jax.movies.data.mapper.FilmsMapper
import com.jax.movies.data.mapper.MoviesMapper
import com.jax.movies.data.remote.api.MoviesApiService
import com.jax.movies.data.remote.model.films.ActorDetailInfoResponse
import com.jax.movies.data.remote.model.films.ActorDto
import com.jax.movies.data.remote.model.films.FilmDto
import com.jax.movies.data.remote.model.films.GalleryImageContainerDto
import com.jax.movies.data.remote.model.films.GalleryResponse
import com.jax.movies.data.remote.model.films.SimilarFilmsResponse
import com.jax.movies.data.remote.model.films.SimilarMovieDto
import com.jax.movies.data.remote.model.home.CountryNameContainerDto
import com.jax.movies.data.remote.model.home.DetailResponseDto
import com.jax.movies.data.remote.model.home.GenreNameContainerDto
import com.jax.movies.data.remote.model.home.MovieDto
import com.jax.movies.data.remote.model.home.MoviesResponseDto
import com.jax.movies.data.remote.model.search.SearchResponseDto
import com.jax.movies.data.repository.DetailMovieRepositoryImpl
import com.jax.movies.data.repository.MoviesRepositoryImpl
import com.jax.movies.data.repository.ProfileRepositoryImpl
import com.jax.movies.data.repository.SearchRepositoryImpl
import com.jax.movies.data.store.OnBoardingSettingStore
import com.jax.movies.di.FilmApiBaseUrl
import com.jax.movies.di.ViewModelModule
import com.jax.movies.domain.entity.films.Actor
import com.jax.movies.domain.entity.films.ActorType
import com.jax.movies.domain.entity.films.GalleryImage
import com.jax.movies.domain.entity.home.CountryNameContainer
import com.jax.movies.domain.entity.home.GenreNameContainer
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.entity.home.MoviesType
import com.jax.movies.domain.repository.DetailMovieRepository
import com.jax.movies.domain.repository.MoviesRepository
import com.jax.movies.domain.repository.ProfileRepository
import com.jax.movies.domain.repository.SearchRepository
import com.jax.movies.domain.usecase.GetActorDetailInfoUseCase
import com.jax.movies.domain.usecase.GetActorDetailInfoUseCaseImpl
import com.jax.movies.domain.usecase.GetActorsUseCase
import com.jax.movies.domain.usecase.GetActorsUseCaseImpl
import com.jax.movies.domain.usecase.GetDetailMovieUseCase
import com.jax.movies.domain.usecase.GetDetailMovieUseCaseImpl
import com.jax.movies.domain.usecase.GetGalleriesUseCase
import com.jax.movies.domain.usecase.GetGalleriesUseCaseImpl
import com.jax.movies.domain.usecase.GetIsEnteredBeforeValueUseCase
import com.jax.movies.domain.usecase.GetIsEnteredBeforeValueUseCaseImpl
import com.jax.movies.domain.usecase.GetMovieCollectionUseCase
import com.jax.movies.domain.usecase.GetMovieCollectionUseCaseImpl
import com.jax.movies.domain.usecase.GetSimilarMoviesUseCase
import com.jax.movies.domain.usecase.GetSimilarMoviesUseCaseImpl
import com.jax.movies.domain.usecase.UpdateIsEnteredBeforeUseCase
import com.jax.movies.domain.usecase.movie.SaveFavouriteMovieUseCase
import com.jax.movies.domain.usecase.movie.SaveFavouriteMovieUseCaseImpl
import com.jax.movies.domain.usecase.movie.SaveSeenMovieUseCase
import com.jax.movies.domain.usecase.movie.SaveSeenMovieUseCaseImpl
import com.jax.movies.domain.usecase.profile.GetFavouriteMoviesUseCase
import com.jax.movies.domain.usecase.profile.GetFavouriteMoviesUseCaseImpl
import com.jax.movies.domain.usecase.profile.GetSeenMoviesUseCase
import com.jax.movies.domain.usecase.profile.GetSeenMoviesUseCaseImpl
import com.jax.movies.domain.usecase.search.SearchQueryUseCase
import com.jax.movies.domain.usecase.search.SearchQueryUseCaseImpl
import com.jax.movies.navigation.detail.Details
import com.jax.movies.navigation.detail.actorNavGraph
import com.jax.movies.navigation.detail.detailsScreens
import com.jax.movies.navigation.detail.filmographyNavGraph
import com.jax.movies.navigation.detail.galleryNavGraph
import com.jax.movies.navigation.detail.movieDetailGraph
import com.jax.movies.navigation.detail.moviesCollectionNavGraph
import com.jax.movies.navigation.main.homeNavGraph
import com.jax.movies.navigation.main.profileNavGraph
import com.jax.movies.navigation.root.GRAPH
import com.jax.movies.navigation.root.NavigationState
import com.jax.movies.navigation.root.onBoardingScreen
import com.jax.movies.navigation.root.rememberNavigationState
import com.jax.movies.navigation.search.SearchGraph
import com.jax.movies.navigation.search.SearchGraph.Companion.COUNTRY_PARAMETER
import com.jax.movies.navigation.search.SearchGraph.Companion.DEFAULT_COUNTRY
import com.jax.movies.navigation.search.SearchGraph.Companion.DEFAULT_GENRE
import com.jax.movies.navigation.search.SearchGraph.Companion.DEFAULT_PERIOD
import com.jax.movies.navigation.search.SearchGraph.Companion.GENRE_PARAMETER
import com.jax.movies.navigation.search.SearchGraph.Companion.PERIOD_PARAMETER
import com.jax.movies.navigation.search.country
import com.jax.movies.navigation.search.genre
import com.jax.movies.navigation.search.period
import com.jax.movies.navigation.search.searchCountryNavGraph
import com.jax.movies.navigation.search.searchGenreNavGraph
import com.jax.movies.navigation.search.searchNavGraph
import com.jax.movies.navigation.search.searchPeriodNavGraph
import com.jax.movies.navigation.search.searchSettingNavGraph
import com.jax.movies.presentation.components.FetchedImage
import com.jax.movies.presentation.components.LoadingItem
import com.jax.movies.presentation.components.StepTitle
import com.jax.movies.presentation.home.actor.ActorDetailScreen
import com.jax.movies.presentation.home.actor.ActorDetailState
import com.jax.movies.presentation.home.actor.ActorDetailViewModel
import com.jax.movies.presentation.home.actor.ActorScreenAction
import com.jax.movies.presentation.home.actor.ActorScreenIntent
import com.jax.movies.presentation.home.filmography.FilmographyScreen
import com.jax.movies.presentation.home.filmography.FilmographyScreenAction
import com.jax.movies.presentation.home.filmography.FilmographyScreenIntent
import com.jax.movies.presentation.home.filmography.FilmographyScreenState
import com.jax.movies.presentation.home.filmography.FilmographyViewModel
import com.jax.movies.presentation.home.gallery.GalleryScreen
import com.jax.movies.presentation.home.gallery.GalleryScreenAction
import com.jax.movies.presentation.home.gallery.GalleryScreenIntent
import com.jax.movies.presentation.home.gallery.GalleryScreenState
import com.jax.movies.presentation.home.gallery.GalleryViewModel
import com.jax.movies.presentation.home.main.HomePage
import com.jax.movies.presentation.home.main.HomeScreenIntent
import com.jax.movies.presentation.home.main.HomeScreenState
import com.jax.movies.presentation.home.main.HomeViewModel
import com.jax.movies.presentation.home.movie.MovieContent
import com.jax.movies.presentation.home.movie.MovieDetailViewModel
import com.jax.movies.presentation.home.movie.MovieScreenAction
import com.jax.movies.presentation.home.movie.MovieScreenIntent
import com.jax.movies.presentation.home.movies.MoviesDetailScreen
import com.jax.movies.presentation.home.movies.MoviesDetailViewModel
import com.jax.movies.presentation.home.movies.MoviesScreenAction
import com.jax.movies.presentation.home.movies.MoviesScreenIntent
import com.jax.movies.presentation.home.movies.MoviesScreenState
import com.jax.movies.presentation.onboarding.OnBoardingPageItem.Companion.onboardingPages
import com.jax.movies.presentation.onboarding.OnBoardingScreen
import com.jax.movies.presentation.onboarding.OnBoardingScreenIntent
import com.jax.movies.presentation.onboarding.OnBoardingViewModel
import com.jax.movies.presentation.profile.CollectionItem
import com.jax.movies.presentation.profile.ProfileScreen
import com.jax.movies.presentation.profile.ProfileScreenIntent
import com.jax.movies.presentation.profile.ProfileScreenState
import com.jax.movies.presentation.profile.ProfileScreenViewModel
import com.jax.movies.presentation.search.country.CountryScreen
import com.jax.movies.presentation.search.genre.GenreScreen
import com.jax.movies.presentation.search.main.SearchScreen
import com.jax.movies.presentation.search.main.SearchScreenIntent
import com.jax.movies.presentation.search.main.SearchScreenState
import com.jax.movies.presentation.search.main.SearchViewModel
import com.jax.movies.presentation.search.period.PeriodScreen
import com.jax.movies.presentation.search.setting.FilterType
import com.jax.movies.presentation.search.setting.SearchSettingScreen
import com.jax.movies.presentation.search.setting.SearchSettingScreenIntent
import com.jax.movies.presentation.search.setting.SearchSettingScreenState
import com.jax.movies.presentation.search.setting.SearchSettingViewModel
import com.jax.movies.presentation.search.setting.ShowType
import com.jax.movies.presentation.search.setting.SortingType
import com.jax.movies.presentation.theme.Blue1
import com.jax.movies.presentation.theme.MoviesTheme
import com.jax.movies.presentation.theme.Typography
import com.jax.movies.utils.Constants.FAVOURITE_MOVIES_TABLE
import com.jax.movies.utils.Constants.FILM_API_BASE_URL
import com.jax.movies.utils.Constants.MOVIE_DATABASE
import com.jax.movies.utils.Constants.SEEN_MOVIES_TABLE
import com.jax.movies.utils.DefaultLists.defaultCollectionTypes
import com.jax.movies.utils.DefaultLists.defaultCountries
import com.jax.movies.utils.DefaultLists.defaultFilterTypes
import com.jax.movies.utils.DefaultLists.defaultGenres
import com.jax.movies.utils.DefaultLists.defaultMovieTypes
import com.jax.movies.utils.DefaultLists.yearsList
import com.jax.movies.utils.Resource
import com.jax.movies.utils.checkRealCount
import com.jax.movies.utils.toPlayRole
import com.valentinilk.shimmer.shimmer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

// DATABASE
class Converters {

    @TypeConverter
    fun fromGenreList(genres: List<GenreNameContainer>): String {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun toGenreList(genresString: String): List<GenreNameContainer> {
        val type = object : TypeToken<List<GenreNameContainer>>() {}.type
        return Gson().fromJson(genresString, type)
    }

    @TypeConverter
    fun fromCountriesLIst(countries: List<CountryNameContainer>): String {
        return Gson().toJson(countries)
    }

    @TypeConverter
    fun toCountriesList(countriesString: String): List<CountryNameContainer> {
        val type = object : TypeToken<List<CountryNameContainer>>() {}.type
        return Gson().fromJson(countriesString, type)
    }
}

@Immutable
@Entity(tableName = FAVOURITE_MOVIES_TABLE)
data class FavouriteMovie(
    @PrimaryKey
    val id: Long,
    val name: String,
    val year: Int,
    val posterUrl: String,
    val ratingKp: Double,
    val slogan: String,
    val shortDescription: String,
    val description: String,
    val lengthMovie: String,
    val ageLimit: String,
    val genres: List<GenreNameContainer>,
    val countries: List<CountryNameContainer>,
)

@Immutable
@Entity(tableName = SEEN_MOVIES_TABLE)
data class SeenMovie(
    @PrimaryKey
    val id: Long,
    val name: String,
    val year: Int,
    val posterUrl: String,
    val ratingKp: Double,
    val slogan: String,
    val shortDescription: String,
    val description: String,
    val lengthMovie: String,
    val ageLimit: String,
    val genres: List<GenreNameContainer>,
    val countries: List<CountryNameContainer>,
)

@Dao
interface FavouriteMoviesDao {
    @Upsert
    suspend fun saveFavouriteMovie(movie: FavouriteMovie): Long

    @Query("DELETE FROM $FAVOURITE_MOVIES_TABLE")
    suspend fun deleteAllFavouriteMovies()

    @Query("SELECT * FROM $FAVOURITE_MOVIES_TABLE")
    fun getAllFavouriteMovies(): Flow<List<FavouriteMovie>>
}

@Dao
interface SeenMoviesDao {
    @Upsert
    suspend fun saveSeenMovie(movie: SeenMovie): Long

    @Query("DELETE FROM $SEEN_MOVIES_TABLE")
    suspend fun deleteAllSeenMovies()

    @Query("SELECT * FROM $SEEN_MOVIES_TABLE")
    fun getAllSeenMovies(): Flow<List<SeenMovie>>
}

@Database(entities = [FavouriteMovie::class, SeenMovie::class], version = 1)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract val favouriteMoviesDao: FavouriteMoviesDao
    abstract val seenMoviesDao: SeenMoviesDao
}

//MAPPER
class FilmsMapper @Inject constructor() {

    fun actorsDtoToEntity(actorsDto: List<ActorDto>?): List<Actor> {
        return actorsDto?.map {
            it.actorDtoToEntity()
        } ?: emptyList()
    }

    private fun ActorDto.actorDtoToEntity(): Actor {
        return Actor(
            actorId = this.actorId,
            nameRu = this.nameRu ?: "",
            nameEn = this.nameEn ?: "",
            description = this.description ?: "",
            professionKeys = professionsToList(this.professionKey),
            profession = this.professionKey,
            posterUrl = this.posterUrl,
            movies = emptyList(),
            allMovies = emptyMap()
        )
    }

    fun galleriesDtoToEntity(galleries: List<GalleryImageContainerDto>?): List<GalleryImage> {
        return galleries?.map { galleryImageDto ->
            galleryImageToEntity(galleryImageDto)
        } ?: emptyList()
    }

    private fun galleryImageToEntity(galleryImageDto: GalleryImageContainerDto): GalleryImage {
        return GalleryImage(
            imageUrl = galleryImageDto.imageUrl ?: ""
        )
    }

    fun actorDetailInfoToActor(
        actorDto: ActorDetailInfoResponse,
        movies: Map<ActorType, List<Movie>>
    ): Actor {
        val professionKeys = professionsToList(actorDto.professions)

        Log.d("ActorMapping", "Movies Map: $movies")
        Log.d("ActorMapping", "Profession Keys: $professionKeys")

        val selectedMovies = movies[professionKeys[0]] ?: emptyList()
        Log.d("ActorMapping", "Selected Movies: $selectedMovies")

        val actor = Actor(
            actorId = actorDto.actorId,
            nameEn = actorDto.nameEn,
            nameRu = actorDto.nameRu,
            posterUrl = actorDto.posterUrl,
            description = "",
            professionKeys = professionKeys,
            profession = actorDto.professions,
            movies = selectedMovies,
            allMovies = movies
        )
        */
/* Log.d("actorssssssssssssssssssssssssssss",actor.toString())*//*

        Log.d("yyyyyyyyyyyyyyyyyyyyyyyy", actor.allMovies.toString())
        Log.d("lllllllllllllllllllllllllllllllllllllllllll", actor.movies.toString())
        return actor
    }


    fun professionsToList(professions: String): List<ActorType> {
        return professions.split(",").map { profession ->
            val trimmedProfession = profession.trim()
            Log.d("ActorMapping - professionsToList", "Profession Keys: $trimmedProfession")
            when (trimmedProfession) {
                "WRITE" -> ActorType.WRITE
                "OPERATOR" -> ActorType.OPERATOR
                "EDITOR" -> ActorType.EDITOR
                "COMPOSER" -> ActorType.COMPOSER
                "PRODUCER_USSR" -> ActorType.PRODUCER_USSR
                "TRANSLATOR" -> ActorType.TRANSLATOR
                "DIRECTOR" -> ActorType.DIRECTOR
                "DESIGN" -> ActorType.DESIGN
                "PRODUCER" -> ActorType.PRODUCER
                "Актер" -> ActorType.ACTOR
                "VOICE_DIRECTOR" -> ActorType.VOICE_DIRECTOR
                "UNKNOWN" -> ActorType.UNKNOWN
                else -> ActorType.UNKNOWN
            }
        }
    }

    fun movieEntityToFavourite(movie: Movie): FavouriteMovie {
        return FavouriteMovie(
            id = movie.id,
            name = movie.name,
            year = movie.year,
            lengthMovie = movie.lengthMovie,
            countries = movie.countries,
            genres = movie.genres,
            ratingKp = movie.ratingKp,
            posterUrl = movie.posterUrl,
            slogan = movie.slogan,
            shortDescription = movie.shortDescription,
            description = movie.description,
            ageLimit = movie.ageLimit
        )
    }

    fun movieEntityToSeen(movie: Movie): SeenMovie {
        return SeenMovie(
            id = movie.id,
            name = movie.name,
            year = movie.year,
            lengthMovie = movie.lengthMovie,
            countries = movie.countries,
            genres = movie.genres,
            ratingKp = movie.ratingKp,
            posterUrl = movie.posterUrl,
            slogan = movie.slogan,
            shortDescription = movie.shortDescription,
            description = movie.description,
            ageLimit = movie.ageLimit
        )
    }

    fun favouriteMovieListToMovies(favouriteMovies: List<FavouriteMovie>): List<Movie> {
        return favouriteMovies.map { favouriteMovieToMovie(it) }
    }

    private fun favouriteMovieToMovie(favouriteMovie: FavouriteMovie): Movie {
        return Movie(
            id = favouriteMovie.id,
            name = favouriteMovie.name,
            year = favouriteMovie.year,
            lengthMovie = favouriteMovie.lengthMovie,
            countries = favouriteMovie.countries,
            genres = favouriteMovie.genres,
            ratingKp = favouriteMovie.ratingKp,
            posterUrl = favouriteMovie.posterUrl,
            slogan = favouriteMovie.slogan,
            shortDescription = favouriteMovie.shortDescription,
            description = favouriteMovie.description,
            ageLimit = favouriteMovie.ageLimit
        )
    }

    fun seenMovieListToMovies(seenMovies: List<SeenMovie>): List<Movie> {
        return seenMovies.map { seenMovieToMovie(it) }
    }

    private fun seenMovieToMovie(seenMovie: SeenMovie): Movie {
        return Movie(
            id = seenMovie.id,
            name = seenMovie.name,
            year = seenMovie.year,
            lengthMovie = seenMovie.lengthMovie,
            countries = seenMovie.countries,
            genres = seenMovie.genres,
            ratingKp = seenMovie.ratingKp,
            posterUrl = seenMovie.posterUrl,
            slogan = seenMovie.slogan,
            shortDescription = seenMovie.shortDescription,
            description = seenMovie.description,
            ageLimit = seenMovie.ageLimit
        )
    }
}

class MoviesMapper @Inject constructor() {

    fun movieDtoToEntity(movieDto: MovieDto): Movie {
        return Movie(
            id = movieDto.id,
            name = movieDto.name ?: "",
            year = movieDto.year,
            genres = dtoGenresToEntity(movieDto.genres),
            countries = dtoCountriesToEntity(movieDto.countries),
            posterUrl = movieDto.posterUrl,
            ratingKp = movieDto.ratingKp,
            ageLimit = "",
            slogan = "",
            description = "",
            shortDescription = "",
            lengthMovie = ""
        )
    }

    fun detailDtoToEntity(detailDto: DetailResponseDto, id: Long? = null): Movie {
        return Movie(
            id = id ?: detailDto.id,
            name = detailDto.name ?: "",
            year = detailDto.year,
            genres = dtoGenresToEntity(detailDto.genres),
            countries = dtoCountriesToEntity(detailDto.countries),
            posterUrl = detailDto.posterUrl ?: "",
            ageLimit = detailDto.ageLimit + "+",
            slogan = detailDto.slogan ?: "",
            ratingKp = detailDto.ratingKp,
            description = detailDto.description ?: "",
            shortDescription = detailDto.shortDescription ?: "",
            lengthMovie = detailDto.duration.convertMinutesToAccepted()
        )
    }

    private fun dtoGenresToEntity(genres: List<GenreNameContainerDto>): List<GenreNameContainer> {
        return genres.map {
            GenreNameContainer(it.genre)
        }
    }

    private fun dtoCountriesToEntity(countries: List<CountryNameContainerDto>): List<CountryNameContainer> {
        return countries.map {
            CountryNameContainer(it.country)
        }
    }

    private fun Long.convertMinutesToAccepted(): String {
        val hours = this / 60
        val minutes = this % 60
        return "$hours hour${if (hours != 1L) "s" else ""} ${minutes} minute${if (minutes != 1L) "s" else ""}"
    }
}

//REMOTE API
object MoviesApiFactory {

    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val apiService = retrofit.create(MoviesApiService::class.java)
}

interface MoviesApiService {

    @GET("v2.2/films/premieres")
    suspend fun getPremieres(
        @retrofit2.http.Query("year") year: Int = DEFAULT_YEAR,
        @retrofit2.http.Query("month") month: String = DEFAULT_MONTH,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<MoviesResponseDto>

    @GET("v2.2/films/collections")
    suspend fun getCollection(
        @retrofit2.http.Query("type") type: String,
        @retrofit2.http.Query("page") page: Int = DEFAULT_PAGE,
        @retrofit2.http.Query("ratingFrom") minRating: Int = MINIMUM_RATING_MOVIE,
        @retrofit2.http.Query("ratingTo") maxRating: Int = MAXIMUM_RATING_MOVIE,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<MoviesResponseDto>

    @GET("v2.2/films/{id}")
    suspend fun getDetailMovie(
        @Path("id") id: Long,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<DetailResponseDto>

    @GET("v1/staff")
    suspend fun getActors(
        @retrofit2.http.Query("filmId") id: Long,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<List<ActorDto>>

    @GET("v2.2/films/{filmId}/images")
    suspend fun getGallery(
        @Path("filmId") id: Long,
        @retrofit2.http.Query("type") type: String = GALLERY_TYPE,
        @retrofit2.http.Query("page") page: Int = DEFAULT_PAGE,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<GalleryResponse>

    @GET("v2.2/films/{filmId}/similars")
    suspend fun getSimilarFilms(
        @Path("filmId") id: Long,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<SimilarFilmsResponse>

    @GET("v1/staff/{id}")
    suspend fun getActorDetailInfo(
        @Path("id") id: Long,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<ActorDetailInfoResponse>

    @GET("v2.1/films/search-by-keyword")
    suspend fun searchByQuery(
        @retrofit2.http.Query("keyword") query: String,
        @retrofit2.http.Query("page") page: Int = DEFAULT_PAGE,
        @Header("X-API-KEY") apiKey: String = BuildConfig.API_KEY
    ): Response<SearchResponseDto>

    private companion object {
        const val GALLERY_TYPE = "STILL"
        const val DEFAULT_PAGE = 1
        const val DEFAULT_MONTH = "APRIL"
        const val DEFAULT_YEAR = 2023
        const val MINIMUM_RATING_MOVIE = 8
        const val MAXIMUM_RATING_MOVIE = 10
    }
}

//DTO
data class ActorDetailInfoResponse(
    @SerializedName("personId") val actorId: Long,
    @SerializedName("posterUrl") val posterUrl: String,
    @SerializedName("nameRu") val nameRu: String,
    @SerializedName("nameEn") val nameEn: String,
    @SerializedName("profession") val professions: String,
    @SerializedName("films") val films: List<FilmDto>,
)

data class ActorDto(
    @SerializedName("staffId") val actorId: Long,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("professionText") val professionText: String,
    @SerializedName("professionKey") val professionKey: String,
    @SerializedName("posterUrl") val posterUrl: String
)

data class FilmDto(
    @SerializedName("filmId") val filmId: Long,
    @SerializedName("nameRu") val nameRu: String,
    @SerializedName("nameEn") val nameEn: String,
    @SerializedName("rating") val rating: Double?,
    @SerializedName("description") val description: String,
    @SerializedName("professionKey") val professionKey: String,
)

data class GalleryImageContainerDto(
    @SerializedName("imageUrl") val imageUrl: String?
)
data class GalleryResponse(
    @SerializedName("items") val items: List<GalleryImageContainerDto>
)
data class SimilarFilmsResponse(
    @SerializedName("items") val films: List<SimilarMovieDto>
)
data class SimilarMovieDto (
    @SerializedName("filmId") val id: Long,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("posterUrl") val posterUrl: String,
)
data class CountryNameContainerDto(
    @SerializedName("country") val country: String
)
data class DetailResponseDto(
    @SerializedName("kinoposikId") val id:Long,
    @SerializedName("ratingKinopoisk") val ratingKp: Double,
    @SerializedName("nameRu") val name:String?,
    @SerializedName("posterUrl") val posterUrl:String?,
    @SerializedName("year") val year:Int,
    @SerializedName("slogan") val slogan:String?,
    @SerializedName("shortDescription") val shortDescription: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("ratingAgeLimits") val ageLimit: String?,
    @SerializedName("filmLength") val duration: Long,
    @SerializedName("genres") val genres: List<GenreNameContainerDto>,
    @SerializedName("countries") val countries: List<CountryNameContainerDto>
)
data class GenreNameContainerDto(
    @SerializedName("genre") val genre:String
)
data class MovieDto(
    @SerializedName("kinopoiskId") val id: Long,
    @SerializedName("ratingKinopoisk") val ratingKp: Double,
    @SerializedName("nameRu") val name: String?,
    @SerializedName("year") val year: Int = 0,
    @SerializedName("genres") val genres: List<GenreNameContainerDto>,
    @SerializedName("countries") val countries: List<CountryNameContainerDto>,
    @SerializedName("posterUrl") val posterUrl: String,
)
data class MoviesResponseDto(
    @SerializedName("items") val films: List<MovieDto>
)
data class SearchResponseDto(
    @SerializedName("films") val films: List<MovieDto>,
)

//REPOSITORY
class DetailMovieRepositoryImpl @Inject constructor(
    private val apiService: MoviesApiService,
    private val filmMapper: FilmsMapper,
    private val movieMapper: MoviesMapper,
    private val seenMoviesDao: SeenMoviesDao,
    private val favouriteMoviesDao: FavouriteMoviesDao
) : DetailMovieRepository {

    override suspend fun getActors(filmId: Long): Flow<Resource<List<Actor>>> {
        return flow {
            val response = apiService.getActors(filmId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(filmMapper.actorsDtoToEntity(it)))
                } ?: emit(Resource.Error(Exception("Response body is null")))
            } else {
                emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
            }
        }
    }

    override suspend fun getGalleries(filmId: Long): Flow<Resource<List<GalleryImage>>> {
        return flow {
            val response = apiService.getGallery(filmId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(filmMapper.galleriesDtoToEntity(it.items)))
                } ?: emit(Resource.Error(Exception("Response body is null")))
            } else {
                emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
            }
        }
    }

    override suspend fun getSimilarFilms(filmId: Long): Flow<Resource<List<Movie>>> {
        return flow {
            val response = apiService.getSimilarFilms(filmId)
            val filmIdList = response.body()?.films?.map { it.id } ?: emptyList()
            Log.d("asddsadsa", filmIdList.toString())

            val movies = filmIdList.mapNotNull {
                val movieResponse = apiService.getDetailMovie(it)
                if (movieResponse.isSuccessful) movieResponse.body() else null
            }

            val finalMovies = movies.mapIndexed { index, movie ->
                movieMapper.detailDtoToEntity(movie, filmIdList[index])
            }

            if (finalMovies.isNotEmpty()) {
                emit(Resource.Success(finalMovies))
            } else {
                emit(Resource.Error(Exception("No similar movies found")))
            }
        }
    }

    override suspend fun getActorDetailInfo(actorId: Long): Flow<Resource<Actor>> {
        return flow {
            val response = apiService.getActorDetailInfo(actorId)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    val resultMap = mutableMapOf<ActorType, MutableList<Movie>>()

                    body.films.take(5).forEach { filmDto ->
                        val detailResponse = apiService.getDetailMovie(filmDto.filmId)
                        if (detailResponse.isSuccessful) {
                            val movie =
                                detailResponse.body()?.let { movieMapper.detailDtoToEntity(it) }
                            if (movie != null) {

                                val actorType = filmMapper.professionsToList(filmDto.professionKey)

                                actorType.forEach { type ->
                                    resultMap.getOrPut(type) { mutableListOf() }.add(movie)
                                }
                            }
                        }
                    }
                    Log.d("sdadasdasdsasaasdasd", resultMap.toString())


                    if (resultMap.isNotEmpty()) {
                        Log.d(
                            "sdadasdasdsasadasdasdasda",
                            filmMapper.actorDetailInfoToActor(body, resultMap).toString()
                        )
                        emit(Resource.Success(filmMapper.actorDetailInfoToActor(body, resultMap)))
                    } else {
                        emit(Resource.Error(Exception("Movies list is empty")))
                    }
                } ?: emit(Resource.Error(Exception("Response body is null")))
            } else {
                emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
            }
        }.catch {
            emit(Resource.Error(it))
        }
    }

    override suspend fun saveFavouriteMovie(movie: Movie): Boolean {
        val favouriteMovie = filmMapper.movieEntityToFavourite(movie)
        val insertedId = favouriteMoviesDao.saveFavouriteMovie(favouriteMovie)
        return insertedId != -1L
    }

    override suspend fun saveSeenMovie(movie: Movie): Boolean {
        val seenMovie = filmMapper.movieEntityToSeen(movie)
        val insertedId = seenMoviesDao.saveSeenMovie(seenMovie)
        return insertedId != -1L
    }
}
class MoviesRepositoryImpl @Inject constructor(
    private val moviesMapper: MoviesMapper,
    private val apiService: MoviesApiService,
    private val onBoardingSettingStore: OnBoardingSettingStore,
    private val scope: CoroutineScope,
) : MoviesRepository {

    override suspend fun getMovieCollection(type: MoviesType): StateFlow<Resource<List<Movie>>> =
        flow {
            val response = when (type) {
                MoviesType.TOP_250_MOVIES, MoviesType.COMICS_THEME, MoviesType.TOP_POPULAR_MOVIES ->
                    apiService.getCollection(type.name)

                MoviesType.PREMIERS -> apiService.getPremieres()
            }
            if (response.isSuccessful) {
                response.body()?.let { respBody ->
                    emit(Resource.Success(respBody.films.map { moviesMapper.movieDtoToEntity(it) }))
                } ?: emit(Resource.Error(Exception("Response body is null")))
            }
        }.retry(
            retries = RETRY_COUNT,
            predicate = {
                delay(DELAY_TIME)
                true
            }
        ).catch {
            emit(Resource.Error(it))
        }.stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = Resource.Success(emptyList())
        )

    override suspend fun getDetailInfo(movieId: Long): Flow<Resource<Movie>> = flow {
        val response = apiService.getDetailMovie(movieId)
        if (response.isSuccessful) {
            response.body()?.let {
                emit(Resource.Success(moviesMapper.detailDtoToEntity(it)))
            } ?: emit(Resource.Error(Exception("Response body is null")))
        } else {
            emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
        }
    }.retry(
        retries = RETRY_COUNT,
        predicate = {
            delay(DELAY_TIME)
            true
        }
    ).catch {
        emit(Resource.Error(it))
    }

    */
/*override suspend fun getIsEnteredBeforeValue(): Flow<Resource<Boolean>> =
        onBoardingSettingStore.isEnteredFlow.map { Resource.Success(it) }

    override suspend fun updateIsEntered(isEntered: Boolean) {
        onBoardingSettingStore.updateIsEntered(isEntered)
    }*//*



    private companion object {
        const val RETRY_COUNT = 5L
        const val DELAY_TIME = 3000L
    }
}
class ProfileRepositoryImpl @Inject constructor(
    private val favouriteMoviesDao: FavouriteMoviesDao,
    private val seenMoviesDao: SeenMoviesDao,
    private val filmsMapper: FilmsMapper
) : ProfileRepository {
    override fun getFavouriteMovies(): Flow<List<Movie>> {
        val favouriteMovies = favouriteMoviesDao.getAllFavouriteMovies()
            .map { filmsMapper.favouriteMovieListToMovies(it) }
        return favouriteMovies
    }

    override fun getSeenMovies(): Flow<List<Movie>> {
        val seenMovies = seenMoviesDao.getAllSeenMovies()
            .map { filmsMapper.seenMovieListToMovies(it) }
        return seenMovies
    }
}
class SearchRepositoryImpl @Inject constructor(
    private val apiService: MoviesApiService,
    private val movieMapper: MoviesMapper
) : SearchRepository {
    override suspend fun searchQuery(query: String): Flow<Resource<List<Movie>>> {
        return flow {
            val response = apiService.searchByQuery(query)
            if (response.isSuccessful) {
                val films = response.body()?.films
                if (films != null) {
                    val movies = films.map { movieMapper.movieDtoToEntity(it) }
                    emit(Resource.Success(movies))
                } else {
                    emit(Resource.Error(Exception("Response body is null")))
                }
            } else {
                emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
            }
        }
    }
}

//STORE
class OnBoardingSettingStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val Context.dataStore by preferencesDataStore(OnBoardingSettingFileName)

    val isEnteredFlow = context.dataStore.data.map {
        it[isEnteredKey] ?: false
    }

    suspend fun updateIsEntered(isEntered: Boolean) {
        context.dataStore.edit {
            it[isEnteredKey] = isEntered
        }
    }

    companion object {
        val isEnteredKey = booleanPreferencesKey("is_entered")
        private const val OnBoardingSettingFileName = "file_name_onboarding"
    }
}

//DI
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return Room.databaseBuilder(
            name = MOVIE_DATABASE,
            context = context,
            klass = MovieDatabase::class.java
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavouriteMoviesDao(movieDatabase: MovieDatabase): FavouriteMoviesDao {
        return movieDatabase.favouriteMoviesDao
    }
    @Singleton
    @Provides
    fun provideSeenMoviesDao(movieDatabase: MovieDatabase): SeenMoviesDao {
        return movieDatabase.seenMoviesDao
    }
}
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FilmApiBaseUrl
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideDefaultInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideApiClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @FilmApiBaseUrl
    fun provideFilmBaseUrl(): String {
        return FILM_API_BASE_URL
    }

    @Provides
    fun provideRetrofit(
        @FilmApiBaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesApiService(retrofit: Retrofit): MoviesApiService {
        return retrofit.create(MoviesApiService::class.java)
    }

    @Provides
    fun provideFetchingScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    fun provideOnBoardingSettingStore(
        @ApplicationContext context: Context
    ):OnBoardingSettingStore{
        return OnBoardingSettingStore(context)
    }
}
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMoviesRepository(moviesRepositoryImpl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    abstract fun provideDetailMovieRepository(detailMovieRepository: DetailMovieRepositoryImpl): DetailMovieRepository

    @Binds
    abstract fun provideProfileRepository(profileRepository: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun provideSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository
}
@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelModule {
    fun movieDetailViewModelFactoryProvider(): MovieDetailViewModel.MovieDetailFactory
    fun moviesDetailViewModelFactoryProvider(): MoviesDetailViewModel.MoviesDetailFactory
    fun galleryViewModelFactoryProvider(): GalleryViewModel.GalleryViewModelFactory
    fun filmographyViewModelFactoryProvider(): FilmographyViewModel.FilmographyViewModelFactory
    fun actorDetailViewModelFactoryProvider(): ActorDetailViewModel.ActorDetailViewModelFactory
}

// ENTITY
@Immutable
@Parcelize
data class Actor(
    val actorId: Long,
    val nameRu: String,
    val nameEn: String,
    val posterUrl: String,
    val description: String,
    val professionKeys: List<ActorType>,
    val profession: String,
    val movies: List<Movie>,
    val allMovies: Map<ActorType, List<Movie>>,
) : Parcelable {
    companion object {
        val navType: NavType<Actor> = object : NavType<Actor>(isNullableAllowed = false) {
            override fun get(bundle: Bundle, key: String): Actor? {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(key, Actor::class.java)
                } else {
                    bundle.getParcelable(key)
                }
            }

            override fun parseValue(value: String): Actor {
                return Gson().fromJson(value, Actor::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: Actor) {
                bundle.putParcelable(key, value)
            }
        }
    }
}
enum class ActorType {
    WRITE,
    OPERATOR,
    EDITOR,
    COMPOSER,
    PRODUCER_USSR,
    TRANSLATOR,
    DIRECTOR,
    DESIGN,
    PRODUCER,
    ACTOR,
    VOICE_DIRECTOR,
    UNKNOWN
}
data class Film(
    val filmId: Long,
    val nameRu: String,
    val nameEn: String,
    val rating: Double?,
    val description: String,
    val professionKey: String,
)
data class GalleryImage(
    val imageUrl: String
)

@Immutable
@Parcelize
data class CountryNameContainer(
    val country: String
):Parcelable
@Immutable
@Parcelize
data class GenreNameContainer(
    val genre:String
):Parcelable
@Parcelize
@Immutable
data class Movie(
    val id: Long,
    val name: String,
    val year: Int,
    val posterUrl: String,
    val ratingKp: Double,
    val slogan: String,
    val shortDescription: String,
    val description: String,
    val lengthMovie: String,
    val ageLimit: String,
    val genres: List<GenreNameContainer>,
    val countries: List<CountryNameContainer>,
) : Parcelable {
    companion object {
        val navType: NavType<Movie> = object : NavType<Movie>(isNullableAllowed = false) {
            override fun get(bundle: Bundle, key: String): Movie? {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(key, Movie::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    bundle.getParcelable(key)
                } ?: throw IllegalArgumentException("Movie not found for key: $key")
            }

            override fun parseValue(value: String): Movie {
                return Gson().fromJson(value, Movie::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: Movie) {
                bundle.putParcelable(key, value)
            }
        }

    }
}
enum class MoviesType {
    TOP_250_MOVIES,
    TOP_POPULAR_MOVIES,
    COMICS_THEME,
    PREMIERS
}

// REPOSITORY
interface DetailMovieRepository {
    suspend fun getActors(filmId: Long): Flow<Resource<List<Actor>>>
    suspend fun getGalleries(filmId: Long): Flow<Resource<List<GalleryImage>>>
    suspend fun getSimilarFilms(filmId: Long): Flow<Resource<List<Movie>>>
    suspend fun getActorDetailInfo(actorId: Long): Flow<Resource<Actor>>

    suspend fun saveFavouriteMovie(movie: Movie): Boolean
    suspend fun saveSeenMovie(movie: Movie): Boolean
}
interface MoviesRepository {
    suspend fun getMovieCollection(type: MoviesType): StateFlow<Resource<List<Movie>>>
    suspend fun getDetailInfo(movieId: Long): Flow<Resource<Movie>>

    */
/*suspend fun getIsEnteredBeforeValue(): Flow<Resource<Boolean>>
    suspend fun updateIsEntered(isEntered: Boolean)*//*

}
interface ProfileRepository {
    fun getFavouriteMovies(): Flow<List<Movie>>
    fun getSeenMovies(): Flow<List<Movie>>
}
interface SearchRepository {
    suspend fun searchQuery(query:String): Flow<Resource<List<Movie>>>
}
// USE CASE
interface SaveFavouriteMovieUseCase {
    suspend operator fun invoke(movie: Movie): Boolean
}
class SaveFavouriteMovieUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
) : SaveFavouriteMovieUseCase {
    override suspend operator fun invoke(movie: Movie): Boolean =
        repository.saveFavouriteMovie(movie)
}
interface SaveSeenMovieUseCase {
    suspend operator fun invoke(movie: Movie):Boolean
}
class SaveSeenMovieUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
) : SaveSeenMovieUseCase {
    override suspend fun invoke(movie: Movie): Boolean = repository.saveSeenMovie(movie)
}
interface GetFavouriteMoviesUseCase {
    operator fun invoke(): Flow<List<Movie>>
}
class GetFavouriteMoviesUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : GetFavouriteMoviesUseCase {
    override fun invoke(): Flow<List<Movie>> = repository.getFavouriteMovies()
}
interface GetSeenMoviesUseCase {
    operator fun invoke(): Flow<List<Movie>>
}
class GetSeenMoviesUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository
) : GetSeenMoviesUseCase {
    override fun invoke(): Flow<List<Movie>> = repository.getSeenMovies()
}
interface SearchQueryUseCase {
    suspend operator fun invoke(query:String): Flow<Resource<List<Movie>>>
}
class SearchQueryUseCaseImpl @Inject constructor(
    private val repository: SearchRepository
) : SearchQueryUseCase {
    override suspend fun invoke(query: String): Flow<Resource<List<Movie>>> {
        return repository.searchQuery(query)
    }
}
interface GetActorDetailInfoUseCase {
    suspend operator fun invoke(actorId: Long): Flow<Resource<Actor>>
}
class GetActorDetailInfoUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
) : GetActorDetailInfoUseCase {
    override suspend operator fun invoke(actorId: Long): Flow<Resource<Actor>> {
        return repository.getActorDetailInfo(actorId)
    }
}
interface GetActorsUseCase {
    suspend operator fun invoke(filmId: Long): Flow<Resource<List<Actor>>>
}
class GetActorsUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
): GetActorsUseCase {
    override suspend operator fun invoke(filmId: Long): Flow<Resource<List<Actor>>> {
        return repository.getActors(filmId)
    }
}
interface GetDetailMovieUseCase {
    suspend operator fun invoke(id: Long): Flow<Resource<Movie>>
}
class GetDetailMovieUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository
) : GetDetailMovieUseCase {

    override suspend operator fun invoke(movieId: Long): Flow<Resource<Movie>> {
        return repository.getDetailInfo(movieId)
    }
}
interface GetGalleriesUseCase {
    suspend operator fun invoke(filmId: Long): Flow<Resource<List<GalleryImage>>>
}
class GetGalleriesUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
): GetGalleriesUseCase {
    override suspend operator fun invoke(filmId: Long): Flow<Resource<List<GalleryImage>>> {
        return repository.getGalleries(filmId)
    }
}
interface GetIsEnteredBeforeValueUseCase {
    suspend operator fun invoke(): Flow<Resource<Boolean>>
}
class GetIsEnteredBeforeValueUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository
) : GetIsEnteredBeforeValueUseCase {
    override suspend operator fun invoke(): Flow<Resource<Boolean>> {
        */
/*return repository.getIsEnteredBeforeValue()*//*

        TODO()
    }
}
interface GetMovieCollectionUseCase{
    suspend operator fun invoke(type: MoviesType): StateFlow<Resource<List<Movie>>>
}
class GetMovieCollectionUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository
) : GetMovieCollectionUseCase {
    override suspend operator fun invoke(type: MoviesType): StateFlow<Resource<List<Movie>>> {
        return repository.getMovieCollection(type)
    }
}
interface GetSimilarMoviesUseCase {
    suspend operator fun invoke(filmId: Long): Flow<Resource<List<Movie>>>
}
class GetSimilarMoviesUseCaseImpl @Inject constructor(
    private val repository: DetailMovieRepository
) : GetSimilarMoviesUseCase {
    override suspend operator fun invoke(filmId: Long): Flow<Resource<List<Movie>>> {
        return repository.getSimilarFilms(filmId)
    }
}
interface UpdateIsEnteredBeforeUseCase {
    suspend fun updateIsEntered(isEntered: Boolean)
}
class UpdateIsEnteredBeforeUseCaseImpl @Inject constructor(
    private val repository: MoviesRepository
) : UpdateIsEnteredBeforeUseCase {
    override suspend fun updateIsEntered(isEntered: Boolean) {
        */
/* repository.updateIsEntered(isEntered)*//*

        TODO()
    }
}
//NAVIGATION
fun NavGraphBuilder.actorNavGraph(
    navigationState: NavigationState,
) {
    composable(
        route = Details.ActorsScreen.route,
        arguments = listOf(navArgument(Details.ACTOR_PARAMETER) { type = Actor.navType })
    ) { backStackEntry ->

        val actor = backStackEntry.getActor()
        val factory = EntryPointAccessors.fromActivity(
            activity = LocalContext.current as Activity,
            entryPoint = ViewModelModule::class.java
        ).actorDetailViewModelFactoryProvider()
        val actorViewModel: ActorDetailViewModel = viewModel(
            factory = ActorDetailViewModel.provideActorDetailViewModelFactory(actor, factory)
        )
        val actorDetailIntent =
            actorViewModel.actorNavigationChannel.collectAsStateWithLifecycle(ActorScreenIntent.Default)

        LaunchedEffect(actorDetailIntent.value) {
            when (val currentIntent = actorDetailIntent.value) {
                is ActorScreenIntent.Default -> {}
                is ActorScreenIntent.OnFilmographyClick -> {
                    navigationState.navigateToFilmographyScreen(currentIntent.actor)
                }

                is ActorScreenIntent.OnMovieClick -> {
                    navigationState.navigateToMovieDetailScreen(
                        currentIntent.movie,
                        backRoute = Details.ActorsScreen.getRouteWithArgs(currentIntent.actor)
                    )
                }

                is ActorScreenIntent.OnClickBack -> {
                    navigationState.navHostController.popBackStack()
                }
            }
        }

        ActorDetailScreen(
            actorDetailViewModel = actorViewModel,
            actor = actor
        )
    }
}

private fun NavBackStackEntry.getActor(): Actor {
    return this.arguments?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            it.getParcelable(Details.ACTOR_PARAMETER, Actor::class.java)
        } else {
            @Suppress("DEPRECATION")
            it.getParcelable(Details.ACTOR_PARAMETER)
        }
    } ?: throw IllegalStateException("Actor is missing")
}
fun NavGraphBuilder.detailsScreens(
    navigationState: NavigationState
) {
    navigation(
        route = GRAPH.DETAILS_GRAPH,
        startDestination = Details.MovieScreen.route
    ) {
        movieDetailGraph(navigationState)
        moviesCollectionNavGraph(navigationState)
        actorNavGraph(navigationState)
        filmographyNavGraph(navigationState)
        galleryNavGraph(navigationState)
        filmographyNavGraph(navigationState)
    }
}
sealed class Details(val route: String) {
    data object MovieScreen : Details("$BASE_M0VIE_SCREEN/{$MOVIE_PARAMETER}") {
        fun getRouteWithArgs(movie: Movie): String {
            val jsonMovie = Gson().toJson(movie)
            return "$BASE_M0VIE_SCREEN/${jsonMovie.encode()}"
        }
    }

    data object MoviesScreen : Details("$BASE_MOVIES_SCREEN/{$MOVIE_TYPE_PARAMETER}") {
        fun getRouteWithArgs(movieType: MoviesType): String {
            val jsonMovieType = Gson().toJson(movieType)
            return "$BASE_MOVIES_SCREEN/${jsonMovieType.encode()}"
        }
    }

    data object ActorsScreen : Details("$BASE_ACTOR_SCREEN/{$ACTOR_PARAMETER}") {
        fun getRouteWithArgs(actor: Actor): String {
            val jsonActor = Gson().toJson(actor)
            return "$BASE_ACTOR_SCREEN/${jsonActor.encode()}"
        }
    }

    data object GalleryScreen : Details("$GALLERY_SCREEN/{$GALLERY_IMAGE_PARAMETER}") {
        fun getRouteWithArgs(movie: Movie): String {
            val jsonMovie = Gson().toJson(movie)
            Log.d("sdadasdas", movie.id.toString())
            return "$GALLERY_SCREEN/${jsonMovie.encode()}"
        }
    }

    data object Filmography : Details("$FILMOGRAPHY_SCREEN/{$FILMOGRAPHY_PARAMETER}") {
        fun getRouteWithArgs(actor: Actor): String {
            val jsonActor = Gson().toJson(actor)
            return "$FILMOGRAPHY_SCREEN/${jsonActor.encode()}"
        }
    }

    companion object {
        const val BASE_M0VIE_SCREEN = "MovieScreen"
        const val MOVIE_PARAMETER = "movieParameter"
        const val BASE_MOVIES_SCREEN = "MoviesScreen"
        const val MOVIE_TYPE_PARAMETER = "movieTypeParameter"
        const val BASE_ACTOR_SCREEN = "ActorScreen"
        const val ACTOR_PARAMETER = "actorParameter"
        const val GALLERY_SCREEN = "GalleryScreen"
        const val GALLERY_IMAGE_PARAMETER = "galleryParameter"
        const val FILMOGRAPHY_SCREEN = "FilmographyScreen"
        const val FILMOGRAPHY_PARAMETER = "filmographyParameter"
    }
}

private fun String.encode() = Uri.encode(this)
fun NavGraphBuilder.filmographyNavGraph(
    navigationState: NavigationState,
) {
    composable(
        route = Details.Filmography.route,
        arguments = listOf(navArgument(Details.FILMOGRAPHY_PARAMETER) { type = Actor.navType })
    ) { navBackStackEntry ->
        val actor = navBackStackEntry.getActor()
        val factory = EntryPointAccessors.fromActivity(
            activity = LocalContext.current as Activity,
            entryPoint = ViewModelModule::class.java
        ).filmographyViewModelFactoryProvider()

        val filmographyViewModel:FilmographyViewModel = viewModel(
            factory = FilmographyViewModel.provideFilmographyViewModel(actor,factory)
        )

        val filmographyIntent =
            filmographyViewModel.filmographyNavigationChannel.collectAsStateWithLifecycle(
                FilmographyScreenIntent.Default
            )
        LaunchedEffect(filmographyIntent.value) {
            when (val currentIntent = filmographyIntent.value) {
                is FilmographyScreenIntent.Default -> {}
                is FilmographyScreenIntent.OnClickBack -> {
                    navigationState.navHostController.popBackStack()
                }

                is FilmographyScreenIntent.OnMovieClick -> {
                    navigationState.navigateToMovieDetailScreen(
                        currentIntent.movie,
                        backRoute = Details.Filmography.route
                    )
                }
            }
        }
        FilmographyScreen(actor = actor, filmographyViewModel = filmographyViewModel)
    }
}

private fun NavBackStackEntry.getActor(): Actor {
    return this.arguments?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            it.getParcelable(Details.FILMOGRAPHY_PARAMETER, Actor::class.java)
        } else {
            @Suppress("DEPRECATION")
            it.getParcelable(Details.FILMOGRAPHY_PARAMETER)
        }
    } ?: throw IllegalStateException("Actor is missing")
}
fun NavGraphBuilder.galleryNavGraph(
    navigationState: NavigationState
){
    composable(
        route = Details.GalleryScreen.route,
        arguments = listOf(navArgument(Details.GALLERY_IMAGE_PARAMETER) {
            type = Movie.navType
        })
    ) { navBackStackEntry ->
        val movie = navBackStackEntry.getMovie()
        val factory = EntryPointAccessors.fromActivity(
            activity = LocalContext.current as Activity,
            entryPoint = ViewModelModule::class.java
        ).galleryViewModelFactoryProvider()

        val galleryViewModel:GalleryViewModel = viewModel(
            factory = GalleryViewModel.provideGalleryViewModel(movie,factory)
        )

        val galleriesIntent =
            galleryViewModel.galleryNavigationChannel.collectAsStateWithLifecycle(
                GalleryScreenIntent.Default)
        LaunchedEffect(galleriesIntent.value) {
            when (galleriesIntent.value) {
                GalleryScreenIntent.Default -> {}
                GalleryScreenIntent.OnClickBack -> {
                    navigationState.navHostController.popBackStack()
                }
            }
        }
        GalleryScreen(movie = movie, galleryViewModel = galleryViewModel)
    }
}

private fun NavBackStackEntry.getMovie():Movie{
    return this.arguments?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            it.getParcelable(Details.GALLERY_IMAGE_PARAMETER, Movie::class.java)
        else it.getParcelable(Details.GALLERY_IMAGE_PARAMETER)
    } ?: throw IllegalStateException("Movie is missing")
}
fun NavGraphBuilder.movieDetailGraph(
    navigationState: NavigationState
) {
    composable(
        route = Details.MovieScreen.route,
        arguments = listOf(navArgument(Details.MOVIE_PARAMETER) { type = Movie.navType })
    ) { backStackEntry ->
        val movie = backStackEntry.getMovie()

        val factory = EntryPointAccessors.fromActivity(
            activity = LocalContext.current as Activity,
            entryPoint = ViewModelModule::class.java
        ).movieDetailViewModelFactoryProvider()

        val movieViewModel: MovieDetailViewModel = viewModel(
            factory = MovieDetailViewModel.provideMovieDetailFactory(movie, factory)
        )

        val movieDetailIntent =
            movieViewModel.movieNavigationChannel.collectAsStateWithLifecycle(
                MovieScreenIntent.MovieScreenNavigationIntent.Default
            )
        LaunchedEffect(movieDetailIntent.value) {
            when (val currentIntent = movieDetailIntent.value) {
                is MovieScreenIntent.MovieScreenNavigationIntent.OnActorClick -> {
                    navigationState.navigateToActorScreen(currentIntent.actor)
                }

                is MovieScreenIntent.MovieScreenNavigationIntent.OnBackClicked -> {
                    navigationState.navHostController.popBackStack()
                }

                is MovieScreenIntent.MovieScreenNavigationIntent.OnGalleryClick -> {
                    navigationState.navigateToGalleryScreen(currentIntent.movie)
                }

                is MovieScreenIntent.MovieScreenNavigationIntent.OnMovieClick -> {
                    navigationState.navigateToMovieDetailScreen(
                        movie = currentIntent.toMovie,
                        backRoute = Details.MovieScreen.getRouteWithArgs(currentIntent.fromMovie)
                    )
                }

                is MovieScreenIntent.MovieScreenNavigationIntent.Default -> {}
                is MovieScreenIntent.OnBlindEyeClick -> TODO()
                is MovieScreenIntent.OnFavouriteClick -> TODO()
                is MovieScreenIntent.OnLickClick -> TODO()
                is  MovieScreenIntent.OnMoreClick -> TODO()
                is MovieScreenIntent.OnShareClick -> TODO()
            }
        }
        MovieContent(
            movie = movie,
            movieDetailViewModel = movieViewModel
        )
    }
}

private fun NavBackStackEntry.getMovie(): Movie {
    return this.arguments?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            it.getParcelable(Details.MOVIE_PARAMETER, Movie::class.java)
        else it.getParcelable(Details.MOVIE_PARAMETER)
    } ?: throw IllegalStateException("Movie is missing")
}

fun NavGraphBuilder.moviesCollectionNavGraph(
    navigationState: NavigationState
) {
    composable(Details.MoviesScreen.route) { backStackEntry ->
        val movieType = backStackEntry.getMovieType()

        val factory = EntryPointAccessors.fromActivity(
            activity = LocalContext.current as Activity,
            entryPoint = ViewModelModule::class.java
        ).moviesDetailViewModelFactoryProvider()

        val moviesDetailViewModel: MoviesDetailViewModel = viewModel(
            factory = MoviesDetailViewModel.provideMoviesViewModel(movieType, factory)
        )

        val moviesScreenIntent =
            moviesDetailViewModel.moviesNavigationChannel.collectAsStateWithLifecycle(
                MoviesScreenIntent.Default
            )
        LaunchedEffect(moviesScreenIntent.value) {
            when (val currentIntent = moviesScreenIntent.value) {
                is MoviesScreenIntent.Default -> {}
                is MoviesScreenIntent.OnClickBack -> {
                    navigationState.navHostController.popBackStack()
                }

                is MoviesScreenIntent.OnMovieClick -> {
                    navigationState.navigateToMovieDetailScreen(
                        movie = currentIntent.movie,
                        backRoute = Details.MoviesScreen.route
                    )
                }

            }
        }
        MoviesDetailScreen(
            type = movieType,
            moviesDetailViewModel = moviesDetailViewModel
        )
    }
}

private fun NavBackStackEntry.getMovieType(): MoviesType {
    val movieType = this.arguments?.getString(Details.MOVIE_TYPE_PARAMETER)
        ?: throw IllegalStateException("MovieType is missing")
    return Gson().fromJson(movieType, MoviesType::class.java)
}
sealed class BottomScreenItem(
    val route: String,
    val title: String,
    @DrawableRes val iconId: Int
) {

    data object HomeScreen : BottomScreenItem(
        iconId = R.drawable.icon_home,
        route = HOME,
        title = HOME
    )

    data object SearchScreen : BottomScreenItem(
        iconId = R.drawable.icon_search,
        route = SEARCH,
        title = SEARCH
    )

    data object ProfileScreen : BottomScreenItem(
        iconId = R.drawable.icon_profile,
        route = PROFILE,
        title = PROFILE
    )

    private companion object {
        const val HOME = "home"
        const val SEARCH = "search"
        const val PROFILE = "profile"
    }
}
fun NavGraphBuilder.homeNavGraph(
    navigationState: NavigationState
) {
    composable(com.jax.movies.navigation.main.BottomScreenItem.HomeScreen.route) {
        val homeViewModel = hiltViewModel<HomeViewModel>()
        val homeScreenIntent =
            homeViewModel.homeNavigationChannel.collectAsStateWithLifecycle(HomeScreenIntent.Default)
        LaunchedEffect(homeScreenIntent.value) {
            when (val currentState = homeScreenIntent.value) {
                is HomeScreenIntent.Default -> {}
                is HomeScreenIntent.OnMovieClick -> {
                    navigationState.navigateToMovieDetailScreen(
                        movie = currentState.movie,
                        backRoute = com.jax.movies.navigation.main.BottomScreenItem.HomeScreen.route
                    )
                }

                is HomeScreenIntent.OnMovieTypeClick -> {
                    navigationState.navigateToMoviesScreen(currentState.movieType)
                }

                is HomeScreenIntent.OnProfileScreenClick -> {
                    navigationState.navigateTo(com.jax.movies.navigation.main.BottomScreenItem.ProfileScreen.route)
                }
                is HomeScreenIntent.OnSearchScreenClick -> {
                    navigationState.navigateTo(com.jax.movies.navigation.main.BottomScreenItem.SearchScreen.route)
                }
            }
        }
        val currentRoute = navigationState.navHostController.currentBackStackEntry?.destination?.route.toString()
        HomePage(homeViewModel = homeViewModel, currentRoute = currentRoute)
    }
}
@Composable
fun MainNavGraph() {
    val navigationState = rememberNavigationState()
    NavHost(
        navController = navigationState.navHostController,
        route = GRAPH.MAIN_GRAPH,
        startDestination = com.jax.movies.navigation.main.BottomScreenItem.HomeScreen.route
    ) {
        homeNavGraph(navigationState)
        searchNavGraph(navigationState)
        profileNavGraph(navigationState)
        detailsScreens(navigationState)
        searchSettingNavGraph(navigationState)
        searchPeriodNavGraph(navigationState)
        searchGenreNavGraph(navigationState)
        searchCountryNavGraph(navigationState)
    }
}
fun NavGraphBuilder.profileNavGraph(
    navigationState: NavigationState
) {
    composable(com.jax.movies.navigation.main.BottomScreenItem.ProfileScreen.route) {
        Log.d("dasdasdasdasdsadas", "ProfileNavGraph: Profile")
        val profileScreenViewModel: ProfileScreenViewModel = hiltViewModel<ProfileScreenViewModel>()
        val currentRoute =
            navigationState.navHostController.currentBackStackEntry?.destination?.route.toString()
        val channel = profileScreenViewModel.profileNavigationChannel.collectAsStateWithLifecycle(
            ProfileScreenIntent.Default
        )
        LaunchedEffect(channel.value){
            when (val currentIntent = channel.value) {
                ProfileScreenIntent.Default -> {}
                ProfileScreenIntent.OnHomeClick -> {
                    navigationState.navHostController.navigate(com.jax.movies.navigation.main.BottomScreenItem.HomeScreen.route)
                }

                ProfileScreenIntent.OnSearchClick -> {
                    navigationState.navHostController.navigate(SearchGraph.SearchMain.route)
                }

                is ProfileScreenIntent.OnMovieClick -> {
                    navigationState.navigateToMovieDetailScreen(currentIntent.movie)
                }
            }
        }
        ProfileScreen(
            profileScreenViewModel = profileScreenViewModel,
            currentRoute = currentRoute
        )
    }
}
object GRAPH {
    const val ROOT = "root"
    const val ON_BOARDING_SCREEN = "onBoarding"
    const val MAIN_GRAPH = "main"
    const val DETAILS_GRAPH = "details"
}
class NavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            if (route == GRAPH.MAIN_GRAPH) {
                popUpTo(GRAPH.ON_BOARDING_SCREEN) {
                    inclusive = true
                }
            } else {
                popUpTo(navHostController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }


    fun navigateToMovieDetailScreen(movie: Movie, backRoute: String? = null) {
        navHostController.navigate(Details.MovieScreen.getRouteWithArgs(movie)) {
            */
/*if(backRoute != Details.MovieScreen.route){
                if (backRoute != null) {
                    popUpTo(backRoute) {
                        saveState = true
                    }
                }
            }
            if(backRoute != null) restoreState = true
            launchSingleTop = true*//*

        }
    }

    fun navigateToMoviesScreen(type: MoviesType) {
        navHostController.navigate(Details.MoviesScreen.getRouteWithArgs(type)) {
            popUpTo(Details.MovieScreen.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToActorScreen(actor: Actor) {
        navHostController.navigate(Details.ActorsScreen.getRouteWithArgs(actor)) {
            popUpTo(Details.MoviesScreen.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToGalleryScreen(movie: Movie) {
        navHostController.navigate(Details.GalleryScreen.getRouteWithArgs(movie)) {
            popUpTo(Details.ActorsScreen.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToFilmographyScreen(actor: Actor) {
        navHostController.navigate(Details.Filmography.getRouteWithArgs(actor)) {
            popUpTo(Details.ActorsScreen.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    fun backToSettings(){
        Log.d("TAG", "backToSettings:runned ")
        navHostController.popBackStack(SearchGraph.SearchSetting.route,true)
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}
@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.onBoardingScreen(
    navigationState: NavigationState
) {
    composable(route = GRAPH.ON_BOARDING_SCREEN) {backStackEntry->
        val onBoardingViewModel = hiltViewModel<OnBoardingViewModel>()
        val currentIntent = onBoardingViewModel.onBoardingNavigationChannel
            .collectAsStateWithLifecycle(OnBoardingScreenIntent.Default)
        LaunchedEffect(currentIntent.value) {
            when (currentIntent.value) {
                OnBoardingScreenIntent.Default -> {}
                OnBoardingScreenIntent.OnFinishClicked -> {
                    navigationState.navigateTo(GRAPH.MAIN_GRAPH)
                }
            }
        }
        OnBoardingScreen(onBoardingViewModel)
    }
}
@Composable
fun RootNavGraph() {
    val navigationState = rememberNavigationState()
    NavHost(
        navController = navigationState.navHostController,
        route = GRAPH.ROOT,
        startDestination = GRAPH.ON_BOARDING_SCREEN,
    ) {
        onBoardingScreen(navigationState=navigationState )
        composable(route = GRAPH.MAIN_GRAPH) { com.jax.movies.navigation.main.MainNavGraph() }
    }
}
fun NavGraphBuilder.searchCountryNavGraph(
    navigationState: NavigationState
) {
    composable(SearchGraph.COUNTRY_SCREEN) {
        CountryScreen(
            onClickBack = {
                navigationState.backToSettings()
            },
            onChooseType = {
                navigationState.navHostController.previousBackStackEntry?.savedStateHandle?.set(
                    COUNTRY_PARAMETER,
                    it
                )
            }
        )
    }
}
fun NavGraphBuilder.searchGenreNavGraph(
    navigationState: NavigationState
) {
    composable(SearchGraph.GENRE_SCREEN){
        GenreScreen(
            onClickBack = {
                navigationState.backToSettings()
            },
            onChooseType = {
                navigationState.navHostController.previousBackStackEntry?.savedStateHandle?.set(
                    GENRE_PARAMETER,
                    it
                )
            }
        )
    }
}
sealed class SearchGraph(val route: String) {
    data object SearchMain:SearchGraph(SEARCH_MAIN)
    data class SearchCountry(val country: String) : SearchGraph(COUNTRY_SCREEN)
    data class SearchGenre(val genre: String) : SearchGraph(GENRE_SCREEN)
    data class SearchPeriod(val from: String, val to: String) : SearchGraph(PERIOD_SCREEN)
    data object SearchSetting : SearchGraph(SETTING_SCREEN)
    companion object {
        const val COUNTRY_SCREEN = "country_screen"
        const val GENRE_SCREEN = "genre_screen"
        const val PERIOD_SCREEN = "period_screen"
        const val SETTING_SCREEN = "setting_screen"
        const val SEARCH_MAIN = "search_screen"

        const val COUNTRY_PARAMETER = "country_parameter"
        const val GENRE_PARAMETER = "genre_parameter"
        const val PERIOD_PARAMETER = "period_parameter"

        const val DEFAULT_COUNTRY = "Russia"
        const val DEFAULT_GENRE = "Comic"
        const val DEFAULT_PERIOD = "1999"
    }
}
fun NavGraphBuilder.searchNavGraph(
    navigationState: NavigationState
) {
    composable(SearchGraph.SearchMain.route) {
        val searchViewModel: SearchViewModel = hiltViewModel<SearchViewModel>()
        val channel = searchViewModel.navigationChannel.collectAsStateWithLifecycle(
            initialValue = SearchScreenIntent.Event.Default
        )
        LaunchedEffect(channel.value) {
            when (val currentEvent = channel.value) {
                is SearchScreenIntent.Event.Default -> {}
                is SearchScreenIntent.Event.OnFilterClick -> {
                    navigationState.navigateTo(SearchGraph.SearchSetting.route)
                }
                is SearchScreenIntent.Event.OnHomeClick -> {
                    navigationState.navigateTo(com.jax.movies.navigation.main.BottomScreenItem.HomeScreen.route)
                }
                is SearchScreenIntent.Event.OnMovieClick -> {
                    navigationState.navigateToMovieDetailScreen(currentEvent.movie)
                }
                is SearchScreenIntent.Event.OnProfileClick -> {
                    navigationState.navigateTo(com.jax.movies.navigation.main.BottomScreenItem.ProfileScreen.route)
                }
            }
        }

        val chosenCountry = navigationState.country()?.collectAsState()
        val chosenGenre = navigationState.genre()?.collectAsState()
        val chosenPeriod = navigationState.period()?.collectAsState()
        SearchScreen(
            searchViewModel = searchViewModel,
            currentRoute = navigationState.currentRoute()
        )
    }
}

private fun NavigationState.currentRoute(): String {
    return this.navHostController.currentBackStackEntry?.destination?.route.toString()
}
fun NavigationState.country(): StateFlow<String>? {
    return this.navHostController.currentBackStackEntry?.savedStateHandle?.getStateFlow(COUNTRY_PARAMETER,
        DEFAULT_COUNTRY
    )
}
fun NavigationState.genre(): StateFlow<String>? {
    return this.navHostController.currentBackStackEntry?.savedStateHandle?.getStateFlow(GENRE_PARAMETER,
        DEFAULT_GENRE
    )
}
fun NavigationState.period(): StateFlow<String>? {
    return this.navHostController.currentBackStackEntry?.savedStateHandle?.getStateFlow(
        PERIOD_PARAMETER,
        DEFAULT_PERIOD
    )
}

fun NavGraphBuilder.searchPeriodNavGraph(
    navigationState: NavigationState
) {
    composable(SearchGraph.PERIOD_SCREEN){
        PeriodScreen(
            onClickBack = {
                navigationState.backToSettings()
            },
            onChoosePeriod = {from,to->
                navigationState.navHostController.previousBackStackEntry?.savedStateHandle?.set(
                    PERIOD_PARAMETER,
                    from
                )
            }
        )
    }
}
fun NavGraphBuilder.searchSettingNavGraph(
    navigationState: NavigationState
) {
    composable(SearchGraph.SETTING_SCREEN) {
        val searchSettingViewModel = hiltViewModel<SearchSettingViewModel>()
        val channel = searchSettingViewModel.navigationChannel
            .collectAsStateWithLifecycle(SearchSettingScreenIntent.Event.Default)
        LaunchedEffect(channel.value) {
            when (channel.value) {
                SearchSettingScreenIntent.Event.Default -> {}
                SearchSettingScreenIntent.Event.OnBackClick -> {
                    navigationState.navHostController.popBackStack()
                }

                SearchSettingScreenIntent.Event.OnCountryClick -> {
                    navigationState.navigateTo(SearchGraph.COUNTRY_SCREEN)
                }

                SearchSettingScreenIntent.Event.OnGenreClick -> {
                    navigationState.navigateTo(SearchGraph.GENRE_SCREEN)
                }

                SearchSettingScreenIntent.Event.OnPeriodClick -> {
                    navigationState.navigateTo(SearchGraph.PERIOD_SCREEN)
                }
            }
        }
        val chosenCountry = navigationState.country()?.collectAsState()
        val chosenGenre = navigationState.genre()?.collectAsState()
        val chosenPeriod = navigationState.period()?.collectAsState()
        SearchSettingScreen(
            searchSettingViewModel = searchSettingViewModel,
            country = chosenCountry?.value ?: DEFAULT_COUNTRY,
            genre = chosenGenre?.value ?: DEFAULT_GENRE,
            period = chosenPeriod?.value ?: DEFAULT_PERIOD
        )
    }
}

// PRESENTATION
@Composable
fun ActorItem(
    onActorClick: (Actor) -> Unit,
    actor: Actor,
    modifierForParent: Modifier = Modifier,
    modifierForImage: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifierForParent
    ) {
        FetchedImage(
            linkToImage = actor.posterUrl,
            modifierForParent = modifierForImage
                .width(49.dp)
                .height(68.dp)
                .clickable {
                    onActorClick(actor)
                    Log.d("dsaadas", actor.actorId.toString())
                }
        )
        Column {
            Text(
                text = actor.nameRu,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                color = Color(0xFF272727),
                overflow = TextOverflow.Ellipsis,
                lineHeight = 13.2.sp,
                modifier = Modifier.sizeIn(maxWidth = 126.dp)
            )
            Text(
                text = actor.nameEn,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = Color(0xFFB5B5C9),
                overflow = TextOverflow.Ellipsis,
                lineHeight = 13.2.sp,
                modifier = Modifier.sizeIn(maxWidth = 126.dp)
            )
        }
    }
}
@Composable
fun BottomNavigationBar(
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit,
    onHomeClick: () -> Unit,
    currentRoute: String?,
) {

    val listItems2 = listOf(
        com.jax.movies.navigation.main.BottomScreenItem.HomeScreen,
        com.jax.movies.navigation.main.BottomScreenItem.SearchScreen,
        com.jax.movies.navigation.main.BottomScreenItem.ProfileScreen,
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        listItems2.forEach { item ->
            AddItem(
                item = item,
                currentRoute = currentRoute.toString(),
                onClick = {
                    when (item) {
                        is com.jax.movies.navigation.main.BottomScreenItem.HomeScreen -> {
                            onHomeClick()
                        }

                        is com.jax.movies.navigation.main.BottomScreenItem.SearchScreen -> {
                            onSearchClick()
                        }

                        is com.jax.movies.navigation.main.BottomScreenItem.ProfileScreen -> {
                            onProfileClick()
                        }
                    }
                }
            )
        }

    }
}

@Composable
private fun RowScope.AddItem(
    item: com.jax.movies.navigation.main.BottomScreenItem,
    currentRoute: String,
    onClick: (String) -> Unit
) {
    NavigationBarItem(
        selected = currentRoute == item.route,
        onClick = { onClick(item.route) },
        icon = {
            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = null,
                tint = if (currentRoute == item.route) Blue1 else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        },
        label = {
            Text(text = item.title)
        }
    )
}
@Composable
fun ErrorItem(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Something went wrong")
    }
}
@Composable
fun ErrorScreen(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Log.d("dsadasdasasdas", "ErrorScreen: $errorMessage")
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = errorMessage, color = Color.Red)
    }
}
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FetchedImage(
    linkToImage: String,
    modifierForParent: Modifier = Modifier,
    modifierForImage: Modifier = Modifier
) {
    GlideSubcomposition(
        modifier = modifierForParent,
        model = linkToImage,
        content = {
            when (state) {
                is RequestState.Failure -> com.jax.movies.presentation.components.ErrorItem()
                is RequestState.Loading -> LoadingItem()
                is RequestState.Success -> Image(
                    painter = painter,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = modifierForImage
                )
            }
        }
    )
}
@Composable
fun LoadingItem(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.sizeIn(maxHeight = 100.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Log.d("dsadasdasasdas", "Loading Screen")
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(color = Color.Red)
    }
}
@Composable
fun MovieItem(
    movie: Movie,
    vertically: Boolean = true,
    ratingPosition: Alignment = Alignment.TopEnd,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {

    val layout: @Composable (modifier: Modifier, content: @Composable () -> Unit) -> Unit =
        if (vertically) {
            { mod, content ->
                Column(
                    modifier = mod,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = { content() })
            }
        } else {
            { mod, content ->
                Row(
                    modifier = mod,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    content = { content() }
                )
            }
        }

    layout(
        modifier = modifier
            .padding(8.dp)
            .then(
                if (vertically) Modifier
                    .wrapContentHeight()
                    .width(150.dp) else Modifier.fillMaxWidth()
            )
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clickable { onMovieClick(movie) },
            contentAlignment = ratingPosition
        ) {
            FetchedImage(
                linkToImage = movie.posterUrl,
                modifierForParent = Modifier
                    .sizeIn(maxHeight = 150.dp, maxWidth = 150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                modifierForImage = Modifier
            )
            if (movie.ratingKp != 0.0) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp)
                        .background(Color.Blue.copy(alpha = 0.6f), AbsoluteRoundedCornerShape(4.dp))
                ) {
                    Text(
                        text = movie.ratingKp.toString(),
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Column {
            Text(
                text = movie.name.takeIf { it.isNotEmpty() } ?: "No Name",
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = movie.genres.firstOrNull()?.genre.toString(),
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.width(100.dp)
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    onNavClick: () -> Unit,
    navIcon: Int,
    title: String?,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            if (!title.isNullOrEmpty()) {
                Text(
                    text = title,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavClick) {
                Icon(
                    painter = painterResource(id = navIcon),
                    contentDescription = null
                )
            }
        },
        modifier = modifier
            .statusBarsPadding()
    )
}

@Composable
fun PeriodSection(
    onPeriodChooseClick: (String,String) -> Unit,
    onClickBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            com.jax.movies.presentation.components.MyTopAppBar(
                onNavClick = onClickBack,
                navIcon = R.drawable.icon_back,
                title = "Период"
            )
        }
    ) { padding ->
        var chosenYear by remember {
            mutableIntStateOf(1998)
        }
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            DatePicker(
                title = "Искать в период с",
                years = yearsList,
                onYearClick = {
                    chosenYear = it
                },
                onBackYearClick = {},
                onNextYearClick = {}
            )
            DatePicker(
                title = "Искать в период до",
                years = yearsList,
                onYearClick = {
                    chosenYear = it
                },
                onBackYearClick = {},
                onNextYearClick = {}
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        onPeriodChooseClick("chosenYear","chosenYear")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D3BFF)),
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Выбрать",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun DatePicker(
    title: String,
    years: List<String>,
    onBackYearClick: () -> Unit,
    onNextYearClick: () -> Unit,
    onYearClick: (Int) -> Unit,
    yearFrom: Int = 1998,
    yearTo: Int = 2009,
    modifier: Modifier = Modifier
) {
    Column(modifier.padding(16.dp)) {
        Text(
            text = title,
            color = Color(0xFF838390),
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Box(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(0xFF272727)
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = "$yearFrom-$yearTo",
                        color = Color(0xFF3D3BFF),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500
                    )
                    IconMix(
                        firstIconClick = onBackYearClick,
                        secondIconClick = onNextYearClick
                    )
                }
                YearsGrid(
                    years = years,
                    onYearClick = onYearClick,
                    modifier = Modifier.padding(horizontal = 40.dp, vertical = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun IconMix(
    firstIconClick: () -> Unit,
    secondIconClick: () -> Unit,
    firstIcon: Int = R.drawable.icon_before_year,
    secondIcon: Int = R.drawable.icon_next_year,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        IconButton(onClick = firstIconClick) {
            Icon(
                painter = painterResource(id = firstIcon),
                contentDescription = "before year",
                modifier = Modifier.size(15.dp)
            )
        }
        IconButton(onClick = secondIconClick) {
            Icon(
                painter = painterResource(id = secondIcon),
                contentDescription = "before year",
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

@Composable
private fun YearsGrid(
    onYearClick: (Int) -> Unit,
    years: List<String>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.padding(start = 12.dp)
    ) {
        years.forEach {
            item {
                Text(
                    text = it,
                    color = Color(0xFF272727),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onYearClick(it.toInt()) }
                )
            }
        }
    }
}
@Composable
fun RelatedMoviesSection(
    title: String = "Похожие фильмы",
    onMovieClick: (Movie) -> Unit,
    countOrAll: String,
    relatedMovies: List<Movie>,
    deleteMoviesIcon: Boolean = false,
    deleteMoviesIconClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        StepTitle(
            title = title,
            countOrOther = countOrAll,
            modifier = modifier,
            onTitleClick = {

            }
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            relatedMovies.forEach { similarMovie ->
                item {
                    com.jax.movies.presentation.components.MovieItem(
                        movie = similarMovie,
                        onMovieClick = onMovieClick,
                        modifier = Modifier.padding(bottom = 56.dp)
                    )
                }
            }
            if (deleteMoviesIcon) {
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.background(Color.White).padding(8.dp)
                    ){
                        IconButton(onClick = deleteMoviesIconClicked) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_basket),
                                contentDescription = "delete",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    leadingIcon: Int = R.drawable.icon_search,
    placeHolderText: String = stringResource(R.string.searchbarPlaceHolder),
    onSearchClick: (String) -> Unit,
    onTrailingIcon1Click: () -> Unit = {},
    onTrailingIcon2Click: () -> Unit = {},
    trailingIcon1: Int? = null,
    trailingIcon2: Int? = null,
    modifier: Modifier = Modifier
) {
    var searchValue by remember {
        mutableStateOf("")
    }
    androidx.compose.material3.SearchBar(
        query = searchValue,
        onQueryChange = {
            searchValue = it
        },
        onSearch = onSearchClick,
        active = true,
        onActiveChange = {},
        leadingIcon = {
            IconButton(onClick = { onSearchClick(searchValue) }) {
                Icon(
                    painter = painterResource(leadingIcon),
                    contentDescription = "search",
                    modifier = Modifier.size(12.dp)
                )
            }
        },
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onTrailingIcon1Click) {
                    trailingIcon1?.let { painterResource(id = it) }?.let {
                        Icon(
                            painter = it,
                            contentDescription = "sort",
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
                IconButton(onClick = onTrailingIcon2Click) {
                    trailingIcon2?.let { painterResource(id = it) }?.let {
                        Icon(
                            painter = it,
                            contentDescription = "sort",
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        },
        placeholder = {
            Text(
                text = placeHolderText,
                color = Color(0xFF838390),
                fontSize = 14.sp
            )
        },
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(68.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomEnd = 16.dp,
                    bottomStart = 16.dp
                )
            ),
        content = {}
    )
}
@Composable
fun SortTypeSection(
    types: List<String>,
    topBarTitle: String,
    searchBarTitle: String,
    onChooseType: (String) -> Unit,
    onClickBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            com.jax.movies.presentation.components.MyTopAppBar(
                onNavClick = onClickBack,
                navIcon = R.drawable.icon_back,
                title = topBarTitle
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            com.jax.movies.presentation.components.SearchBar(
                leadingIcon = R.drawable.icon_search,
                placeHolderText = searchBarTitle,
                onSearchClick = {},
                modifier = Modifier.padding(16.dp)
            )
            ChooseTypeSection(
                types = types,
                onTypeClick = onChooseType
            )
        }
    }
}

@Composable
private fun ChooseTypeSection(
    onTypeClick: (String) -> Unit,
    types: List<String>
) {
    Column {
        types.forEach {
            Text(
                text = it,
                fontSize = 16.sp,
                color = Color(0xFF272727),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clickable { onTypeClick(it) }
            )
        }
    }
}
@Composable
fun StepTitle(
    onTitleClick: () -> Unit,
    title: String,
    subTitle: String? = null,
    countOrOther: String,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = countOrOther,
                fontWeight = FontWeight.W600,
                fontSize = 14.sp,
                color = Color(0xFF3D3BFF)

            )
            IconButton(onClick = onTitleClick) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = "see all",
                    tint = Color(0xFF3D3BFF)
                )
            }
        }
        if (subTitle?.isNotEmpty() == true) {
            Text(
                text = subTitle,
                fontWeight = FontWeight.W400,
                fontSize = 12.sp,
                color = Color(0xFFB5B5C9),
                modifier = Modifier.weight(1f)
            )
        }
    }
}
@Composable
fun ActorDetailScreen(
    actorDetailViewModel: ActorDetailViewModel,
    actor: Actor,
    modifier: Modifier = Modifier
) {
    val state = actorDetailViewModel.state.collectAsStateWithLifecycle()
    when (val currentState = state.value) {
        is ActorDetailState.Initial -> {}
        is ActorDetailState.Loading -> com.jax.movies.presentation.components.LoadingScreen()
        is ActorDetailState.Error -> com.jax.movies.presentation.components.ErrorScreen(currentState.message)
        is ActorDetailState.Success -> MainContent(
            actor = currentState.actor,
            onFilmographyClick = {
                actorDetailViewModel.handleIntent(ActorScreenIntent.OnFilmographyClick(it))
            },
            onMovieClick = {
                actorDetailViewModel.handleIntent(ActorScreenIntent.OnMovieClick(it,actor))
            },
            onClickBack = {
                actorDetailViewModel.handleIntent(ActorScreenIntent.OnClickBack)
            },
            moviesWithActor = currentState.actor.allMovies.values.flatten(),
            modifier = modifier
        )
    }
    LaunchedEffect(actor.actorId) {
        actorDetailViewModel.handleAction(ActorScreenAction.FetchActorDetailInfo(actor))
    }
}

@Composable
private fun MainContent(
    onClickBack: () -> Unit,
    moviesWithActor: List<Movie>,
    onMovieClick: (Movie) -> Unit,
    onFilmographyClick: (Actor) -> Unit,
    actor: Actor,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            com.jax.movies.presentation.components.MyTopAppBar(
                onNavClick = onClickBack,
                navIcon = R.drawable.icon_back,
                title = ""
            )
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(start = 26.dp,)
        ) {
            com.jax.movies.presentation.components.ActorItem(
                actor = actor,
                onActorClick = {},
                modifierForImage = Modifier
                    .size(width = 146.dp, height = 201.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            ActorBestSection(
                onMovieClick = onMovieClick,
                moviesWithActor = moviesWithActor
            )
            ActorFilmographySection(
                countFilms = moviesWithActor.size,
                onFilmographyClick = { onFilmographyClick(actor) }
            )
        }
    }
}

@Composable
private fun ActorBestSection(
    onMovieClick: (Movie) -> Unit,
    moviesWithActor: List<Movie>,
    modifier: Modifier = Modifier
) {
    com.jax.movies.presentation.components.RelatedMoviesSection(
        title = "Лучшее",
        onMovieClick = onMovieClick,
        countOrAll = "все",
        relatedMovies = moviesWithActor,
        modifier = modifier
    )
}

@Composable
private fun ActorFilmographySection(
    onFilmographyClick: () -> Unit,
    countFilms: Int
) {
    StepTitle(
        title = "Фильмография",
        countOrOther = countFilms.toString(),
        subTitle = "$countFilms фильмов",
        onTitleClick = onFilmographyClick
    )
}
sealed class ActorDetailState {
    data object Initial : ActorDetailState()
    data object Loading : ActorDetailState()
    data class Error(val message: String) : ActorDetailState()
    data class Success(val actor: Actor) : ActorDetailState()
}
@Suppress("UNCHECKED_CAST")
class ActorDetailViewModel @AssistedInject constructor(
    @Assisted private val actor: Actor,
    private val getActorDetailInfoUseCase: GetActorDetailInfoUseCaseImpl
) : ViewModel() {

    private val _state = MutableStateFlow<ActorDetailState>(ActorDetailState.Initial)
    val state: StateFlow<ActorDetailState> = _state.asStateFlow()

    private val _actorNavigationChannel = Channel<ActorScreenIntent>()
    val actorNavigationChannel = _actorNavigationChannel.receiveAsFlow()

    fun handleIntent(intent: ActorScreenIntent) {
        when (intent) {
            is ActorScreenIntent.Default -> {}
            is ActorScreenIntent.OnFilmographyClick -> {
                viewModelScope.launch {
                    _actorNavigationChannel.send(ActorScreenIntent.OnFilmographyClick(intent.actor))
                }
            }

            is ActorScreenIntent.OnMovieClick -> {
                viewModelScope.launch {
                    _actorNavigationChannel.send(
                        ActorScreenIntent.OnMovieClick(
                            intent.movie,
                            intent.actor
                        )
                    )
                }
            }

            ActorScreenIntent.OnClickBack -> {
                viewModelScope.launch {
                    _actorNavigationChannel.send(ActorScreenIntent.OnClickBack)
                }
            }
        }
    }

    fun handleAction(action: ActorScreenAction) {
        when (action) {
            is ActorScreenAction.FetchActorDetailInfo -> fetchDetailInfo(action.actor)
        }
    }

    private fun fetchDetailInfo(actor: Actor) {
        _state.value = ActorDetailState.Loading
        viewModelScope.launch {
            getActorDetailInfoUseCase(actor.actorId).collectLatest { response ->
                when (response) {
                    is Resource.Error -> _state.value =
                        ActorDetailState.Error(response.message.toString())

                    is Resource.Success -> {
                        Log.d("qqqqqqqqqqqqqqqqqqqqqqqqqqq", "Success: ${response.data}")
                        _state.value = ActorDetailState.Success(response.data)
                    }
                }
            }
        }
    }
    @AssistedFactory
    interface ActorDetailViewModelFactory{
        fun create(actor: Actor):ActorDetailViewModel
    }
    companion object{
        fun provideActorDetailViewModelFactory(
            actor: Actor,
            factory: ActorDetailViewModelFactory
        ): ViewModelProvider.Factory = object: ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(actor) as T
            }
        }

    }
}
sealed class ActorScreenAction {
    data class FetchActorDetailInfo(val actor: Actor) : ActorScreenAction()
}
sealed class ActorScreenIntent {
    data object Default : ActorScreenIntent()
    data object OnClickBack : ActorScreenIntent()
    data class OnMovieClick(val movie: Movie,val actor: Actor) : ActorScreenIntent()
    data class OnFilmographyClick(val actor: Actor) : ActorScreenIntent()
}
@Composable
fun FilmographyScreen(
    filmographyViewModel: FilmographyViewModel,
    actor: Actor,
    modifier: Modifier = Modifier,
) {

    val state by filmographyViewModel.state.collectAsStateWithLifecycle()

    when (val currentState = state) {
        is FilmographyScreenState.Initial -> {}
        is FilmographyScreenState.Loading -> com.jax.movies.presentation.components.LoadingScreen()
        is FilmographyScreenState.Error -> com.jax.movies.presentation.components.ErrorScreen(
            currentState.message
        )
        is FilmographyScreenState.Success -> {
            FilmographyContent(
                onClickBack = {
                    filmographyViewModel.handleIntent(FilmographyScreenIntent.OnClickBack)
                },
                onMovieClick = {
                    filmographyViewModel.handleIntent(FilmographyScreenIntent.OnMovieClick(it))
                },
                onFilmographyClick = {
                    filmographyViewModel.handleAction(FilmographyScreenAction.FetchMovies(it))
                },
                movies = currentState.movies,
                actorTypes = filmographyViewModel.actorTypes,
                modifier = modifier
            )
        }
    }

    LaunchedEffect(Unit) {
        filmographyViewModel.handleAction(FilmographyScreenAction.FetchFilmography(actor))
    }
}

@Composable
private fun FilmographyContent(
    onClickBack: () -> Unit,
    onFilmographyClick: (ActorType) -> Unit,
    onMovieClick: (Movie) -> Unit,
    actorTypes: Map<ActorType, List<Movie>>,
    movies: List<Movie>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            com.jax.movies.presentation.components.MyTopAppBar(
                onNavClick = onClickBack,
                navIcon = R.drawable.icon_back,
                title = "Фильмография"
            )
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(it)
        ) {
            item {
                FilmographyTitle(
                    onFilmographyClick = { actorType ->
                        onFilmographyClick(actorType)
                    },
                    actorTypes = actorTypes
                )
            }
            items(movies) { movie ->
                com.jax.movies.presentation.components.MovieItem(
                    movie = movie,
                    onMovieClick = onMovieClick,
                    ratingPosition = Alignment.TopStart,
                    vertically = false,
                    modifier = Modifier.padding(start = 26.dp)
                )
            }
        }
    }
}

@Composable
private fun FilmographyTitle(
    onFilmographyClick: (ActorType) -> Unit,
    actorTypes: Map<ActorType, List<Movie>>,
    modifier: Modifier = Modifier,
) {
    var currentType by remember {
        mutableStateOf(actorTypes.keys.first())
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(start = 26.dp)
    ) {
        actorTypes.forEach { (type, count) ->
            TypeWithCount(
                type = type,
                count = count.checkRealCount(),
                isSelected = currentType == type,
                isSelectedTextColor = Color.White,
                unSelectedTextColor = Color.Black,
                isSelectedContainerColor = Color.Blue,
                unSelectedContainerColor = Color.LightGray,
                onFilmographyClick = {
                    onFilmographyClick(it)
                    currentType = it
                }
            )

        }
    }
}

@Composable
private fun TypeWithCount(
    count: Int,
    type: ActorType,
    isSelected: Boolean,
    isSelectedTextColor: Color = Color.White,
    unSelectedTextColor: Color = Color.Gray,
    isSelectedContainerColor: Color = Color(0xFF3D3BFF),
    unSelectedContainerColor: Color = Color.White,
    onFilmographyClick: (ActorType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (isSelected) isSelectedContainerColor else unSelectedContainerColor
    val textColor = if (isSelected) isSelectedTextColor else unSelectedTextColor
    Box(
        modifier = modifier
            .size(width = 144.dp, height = 36.dp)
            .clip(RoundedCornerShape(56.dp))
            .background(backgroundColor)
            .clickable { onFilmographyClick(type) }
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = type.toPlayRole(),
                color = textColor,
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = count.toString(),
                color = textColor,
                fontSize = 10.sp
            )
        }
    }
}
sealed class FilmographyScreenAction {
    data class FetchMovies(val actorType: ActorType) : FilmographyScreenAction()
    data class FetchFilmography(val actor: Actor) : FilmographyScreenAction()
}
sealed class FilmographyScreenIntent {
    data object Default : FilmographyScreenIntent()
    data object OnClickBack : FilmographyScreenIntent()
    data class OnMovieClick(val movie: Movie) : FilmographyScreenIntent()
}
sealed class FilmographyScreenState {
    data object Initial : FilmographyScreenState()
    data object Loading : FilmographyScreenState()
    data class Error(val message: String) : FilmographyScreenState()
    data class Success(val movies: List<Movie>) : FilmographyScreenState()
    */
/*  data class Success(val movies: Map<ActorType, List<Movie>>) : FilmographyScreenState()*//*

}
@Suppress("UNCHECKED_CAST")
class FilmographyViewModel @AssistedInject constructor(
    @Assisted private val actor: Actor,
    private val getActorDetailInfoUseCase: GetActorDetailInfoUseCaseImpl
) : ViewModel() {

    var actorTypes: Map<ActorType, List<Movie>> = emptyMap()
    private val _state = MutableStateFlow<FilmographyScreenState>(FilmographyScreenState.Initial)
    val state = _state.asStateFlow()
    private val _filmographyNavigationChannel = Channel<FilmographyScreenIntent>()
    val filmographyNavigationChannel = _filmographyNavigationChannel.receiveAsFlow()

    fun handleIntent(intent: FilmographyScreenIntent) {
        when (intent) {
            is FilmographyScreenIntent.Default -> {}
            is FilmographyScreenIntent.OnClickBack -> {
                viewModelScope.launch {
                    _filmographyNavigationChannel.send(FilmographyScreenIntent.OnClickBack)
                }
            }

            is FilmographyScreenIntent.OnMovieClick -> {
                viewModelScope.launch {
                    _filmographyNavigationChannel.send(FilmographyScreenIntent.OnMovieClick(intent.movie))
                }
            }
        }
    }

    fun handleAction(action: FilmographyScreenAction) {
        when (action) {
            is FilmographyScreenAction.FetchFilmography -> {
                fetchFilmography(action.actor)
            }

            is FilmographyScreenAction.FetchMovies -> {
                getMoviesByFilmographyType(action.actorType)
            }
        }
    }

    private fun fetchFilmography(actor: Actor) {
        _state.value = FilmographyScreenState.Loading
        viewModelScope.launch {
            getActorDetailInfoUseCase(actor.actorId).collect { response ->
                when (response) {
                    is Resource.Error -> {
                        _state.value = FilmographyScreenState.Error(response.message.toString())
                    }

                    is Resource.Success -> {
                        */
/*actorTypes = actor.allMovies as? Map<ActorType, List<Movie>> ?: emptyMap()*//*

                        Log.d("Actor", actor.toString())
                        Log.d("ActorAllMovies", actor.allMovies.toString())
                        Log.d("ActorTypes", actorTypes.toString())
                        Log.d(
                            "Profession Keys - first",
                            actorTypes[actor.professionKeys[0]].toString()
                        )
                        Log.d(
                            "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxKEye",
                            actor.professionKeys[0].toString()
                        )
                        Log.d(
                            "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
                            actor.professionKeys.toString()
                        )
                        Log.d(
                            "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy",
                            actorTypes[actor.professionKeys[0]].toString()
                        )
                        */
/*_state.value =
                            actorTypes[actor.professionKeys[0]]?.let {
                                FilmographyScreenState.Success(it)
                            } ?: FilmographyScreenState.Error("No movies found")*//*

                        actorTypes =
                            response.data.allMovies as? Map<ActorType, List<Movie>> ?: emptyMap()
                        _state.value =
                            actorTypes[actor.professionKeys[0]]?.let {
                                FilmographyScreenState.Success(it)
                            } ?: FilmographyScreenState.Error("No movies found")
                    }
                }
            }
        }
    }

    private fun getMoviesByFilmographyType(actorType: ActorType) {
        val movies = actorTypes[actorType]?.toList() ?: emptyList()
        _state.value = FilmographyScreenState.Success(movies)
    }

    @AssistedFactory
    interface FilmographyViewModelFactory {
        fun create(actor: Actor): FilmographyViewModel
    }
    companion object{
        fun provideFilmographyViewModel(
            actor: Actor,
            factory: FilmographyViewModelFactory,
        ):ViewModelProvider.Factory = object:ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(actor) as T
            }
        }
    }
}
@Composable
fun GalleryScreen(
    galleryViewModel: GalleryViewModel,
    movie: Movie
) {
    val state = galleryViewModel.state.collectAsStateWithLifecycle()
    when (val currentState = state.value) {
        is GalleryScreenState.Initial -> {}
        is GalleryScreenState.Loading -> com.jax.movies.presentation.components.LoadingScreen()
        is GalleryScreenState.Error -> com.jax.movies.presentation.components.ErrorScreen(
            currentState.message
        )
        is GalleryScreenState.Success -> GalleryImages(
            galleries = currentState.galleries,
            onClickBack = {
                galleryViewModel.handleIntent(GalleryScreenIntent.OnClickBack)
            }
        )
    }
    LaunchedEffect(movie) {
        galleryViewModel.handleAction(GalleryScreenAction.FetchGalleries(movie))
    }
}

@Composable
private fun GalleryImages(
    onClickBack: () -> Unit,
    galleries: List<GalleryImage>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            com.jax.movies.presentation.components.MyTopAppBar(
                onNavClick = onClickBack,
                navIcon = R.drawable.icon_back,
                title = "Галерея"
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier.padding(padding),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(galleries.chunked(2)) { index, pair ->
                if (index % 2 == 0) {
                    FetchedImage(
                        linkToImage = pair[0].imageUrl,
                        modifierForParent = Modifier
                            .fillMaxWidth()
                            .height(92.dp)
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FetchedImage(
                            linkToImage = pair[0].imageUrl,
                            modifierForParent = Modifier
                                .weight(1f)
                                .height(92.dp),
                            modifierForImage = Modifier.clip(RoundedCornerShape(8.dp))
                        )
                        FetchedImage(
                            linkToImage = pair[1].imageUrl,
                            modifierForParent = Modifier
                                .weight(1f)
                                .height(92.dp),
                            modifierForImage = Modifier.clip(RoundedCornerShape(8.dp))
                        )
                    }
                }
            }
        }

    }
}
sealed class GalleryScreenAction {
    data class FetchGalleries(val movie: Movie) : GalleryScreenAction()
}
sealed class GalleryScreenIntent {
    data object Default : GalleryScreenIntent()
    data object OnClickBack : GalleryScreenIntent()
}
sealed class GalleryScreenState {
    data object Initial : GalleryScreenState()
    data object Loading : GalleryScreenState()
    data class Error(val message: String) : GalleryScreenState()
    data class Success(val galleries: List<GalleryImage>) : GalleryScreenState()
}
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
@Composable
fun HomePage(
    homeViewModel: HomeViewModel,
    currentRoute: String = com.jax.movies.navigation.main.BottomScreenItem.HomeScreen.route,
    modifier: Modifier = Modifier
) {
    val state = homeViewModel.homeScreenState.collectAsStateWithLifecycle()
    Scaffold(
        bottomBar = {
            com.jax.movies.presentation.components.BottomNavigationBar(
                currentRoute = currentRoute,
                onHomeClick = {},
                onProfileClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnSearchScreenClick)
                },
                onSearchClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnProfileScreenClick)
                },
            )
        }
    ) { innerPadding->
        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(R.drawable.vector),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )
            Spacer(Modifier.height(24.dp))

            HandleMovieList(
                title = "Top 250 Movies",
                moviesState = state.value.top250MoviesState,
                moviesType = MoviesType.TOP_250_MOVIES,
                onListClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieTypeClick(it))
                },
                onMovieClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieClick(it))
                }
            )

            HandleMovieList(
                title = "Popular Movies",
                moviesState = state.value.popularMoviesState,
                moviesType = MoviesType.TOP_POPULAR_MOVIES,
                onListClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieTypeClick(it))
                },
                onMovieClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieClick(it))
                }
            )

            HandleMovieList(
                title = "Comics Theme",
                moviesState = state.value.comicsMoviesState,
                moviesType = MoviesType.COMICS_THEME,
                onListClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieTypeClick(it))
                },
                onMovieClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieClick(it))
                }
            )

            HandleMovieList(
                title = "Premiers",
                moviesState = state.value.premiersMoviesState,
                moviesType = MoviesType.PREMIERS,
                onListClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieTypeClick(it))
                },
                onMovieClick = {
                    homeViewModel.handleIntent(HomeScreenIntent.OnMovieClick(it))
                }
            )
        }
    }
}

@Composable
private fun HandleMovieList(
    title: String,
    moviesState: HomeScreenState.MoviesState,
    moviesType: MoviesType,
    onListClick: (MoviesType) -> Unit,
    onMovieClick: (Movie) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        when (moviesState) {
            is HomeScreenState.MoviesState.Loading -> {
                ShimmerLoadingItems()
            }

            is HomeScreenState.MoviesState.Success -> {
                LazyRowItem(
                    items = moviesState.movies,
                    onListClick = onListClick,
                    moviesType = moviesType,
                    title = title,
                    onMovieClick = onMovieClick
                )
            }

            is HomeScreenState.MoviesState.Error -> com.jax.movies.presentation.components.ErrorScreen(
                errorMessage = moviesState.message
            )
            HomeScreenState.MoviesState.Initial -> Unit
        }
    }
}

@Composable
private fun LazyRowItem(
    items: List<Movie>,
    title: String,
    moviesType: MoviesType,
    onMovieClick: (Movie) -> Unit,
    onListClick: (MoviesType) -> Unit
) {
    var wrapped by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            item {
                Text(
                    text = stringResource(R.string.seeAll),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    modifier = Modifier
                        .clickable { onListClick(moviesType) }
                )
            }
        }

        val displayedItems = if (wrapped) items.take(items.size / 2) else items
        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(displayedItems) { movie ->
                com.jax.movies.presentation.components.MovieItem(
                    movie = movie,
                    onMovieClick = { onMovieClick(movie) }
                )
            }
            item {
                val icon = if (wrapped) R.drawable.arrow_right else R.drawable.arrowleft
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { wrapped = !wrapped }
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "See more",
                        tint = Color.Blue,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ShimmerLoadingItems(
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        items(10) {
            Box(
                modifier = Modifier
                    .size(111.dp, 156.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
                    .shimmer()
            )
        }
    }
}

sealed class HomeScreenIntent {
    data object Default : HomeScreenIntent()
    data object OnSearchScreenClick : HomeScreenIntent()
    data object OnProfileScreenClick : HomeScreenIntent()
    data class OnMovieClick(val movie: Movie) : HomeScreenIntent()
    data class OnMovieTypeClick(val movieType:MoviesType) : HomeScreenIntent()
}
data class HomeScreenState(
    val top250MoviesState: MoviesState = MoviesState.Initial,
    val popularMoviesState: MoviesState = MoviesState.Initial,
    val comicsMoviesState: MoviesState = MoviesState.Initial,
    val premiersMoviesState: MoviesState = MoviesState.Initial
) {
    sealed interface MoviesState {
        data object Initial : MoviesState
        data object Loading : MoviesState
        data class Error(val message: String) : MoviesState
        data class Success(val movies: List<Movie>) : MoviesState
    }
}
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieCollectionUseCase: GetMovieCollectionUseCaseImpl
) : ViewModel() {
    private val _homeScreenState: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState())
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState.asStateFlow()

    private val _homeNavigationChannel = Channel<HomeScreenIntent>(capacity = Channel.CONFLATED)
    val homeNavigationChannel = _homeNavigationChannel.receiveAsFlow()

    init {
        fetchAllMovies()
    }

    private fun fetchAllMovies() {
        viewModelScope.launch {
            val top250MoviesDeferred = async { fetchMovies(MoviesType.TOP_250_MOVIES) }
            val popularMoviesDeferred = async { fetchMovies(MoviesType.TOP_POPULAR_MOVIES) }
            val comicsMoviesDeferred = async { fetchMovies(MoviesType.COMICS_THEME) }
            val premiersMoviesDeferred = async { fetchMovies(MoviesType.PREMIERS) }

            val top250MoviesState = top250MoviesDeferred.await()
            val popularMoviesState = popularMoviesDeferred.await()
            val comicsMoviesState = comicsMoviesDeferred.await()
            val premiersMoviesState = premiersMoviesDeferred.await()

            _homeScreenState.update { currentState ->
                currentState.copy(
                    top250MoviesState = top250MoviesState,
                    popularMoviesState = popularMoviesState,
                    comicsMoviesState = comicsMoviesState,
                    premiersMoviesState = premiersMoviesState
                )
            }
        }
    }

    private fun fetchMovies(type: MoviesType): HomeScreenState.MoviesState {
        viewModelScope.launch {
            updateMovieState(type, HomeScreenState.MoviesState.Loading)
            delay(3000)

            getMovieCollectionUseCase(type).collect { result ->
                val newState = when (result) {
                    is Resource.Error -> HomeScreenState.MoviesState.Error(result.message.cause.toString())
                    is Resource.Success -> HomeScreenState.MoviesState.Success(movies = result.data)
                }
                updateMovieState(type, newState)
            }
        }

        return HomeScreenState.MoviesState.Loading
    }

    private fun updateMovieState(type: MoviesType, newState: HomeScreenState.MoviesState) {
        _homeScreenState.update { currentState ->
            when (type) {
                MoviesType.TOP_250_MOVIES -> currentState.copy(top250MoviesState = newState)
                MoviesType.TOP_POPULAR_MOVIES -> currentState.copy(popularMoviesState = newState)
                MoviesType.COMICS_THEME -> currentState.copy(comicsMoviesState = newState)
                MoviesType.PREMIERS -> currentState.copy(premiersMoviesState = newState)
            }
        }
    }

    fun handleIntent(intent: HomeScreenIntent) {
        when (intent) {
            is HomeScreenIntent.OnMovieClick -> {
                viewModelScope.launch {
                    _homeNavigationChannel.send(HomeScreenIntent.OnMovieClick(intent.movie))
                }
            }
            is HomeScreenIntent.OnMovieTypeClick -> {
                viewModelScope.launch {
                    _homeNavigationChannel.send(HomeScreenIntent.OnMovieTypeClick(intent.movieType))
                }
            }
            is HomeScreenIntent.OnProfileScreenClick -> {
                viewModelScope.launch {
                    _homeNavigationChannel.send(HomeScreenIntent.OnProfileScreenClick)
                }
            }
            is HomeScreenIntent.OnSearchScreenClick -> {
                viewModelScope.launch {
                    _homeNavigationChannel.send(HomeScreenIntent.OnSearchScreenClick)
                }
            }
            HomeScreenIntent.Default -> {}
        }
    }
}

sealed class MovieDetailState {
    data object Initial : MovieDetailState()
    data object Loading : MovieDetailState()
    data class Error(val message: String) : MovieDetailState()
    data class Success(
        val movie: Movie,
        val gallery: List<GalleryImage>,
        val actors: List<Actor>,
        val filmCrew: List<Actor>,
        val similarMovies: List<Movie>
    ) : MovieDetailState()
}
@Suppress("UNCHECKED_CAST")
class MovieDetailViewModel @AssistedInject constructor(
    @Assisted private val movie: Movie,
    private val getDetailMovieUseCaseImpl: GetDetailMovieUseCaseImpl,
    private val getActorsUseCaseImpl: GetActorsUseCaseImpl,
    private val getGalleriesUseCaseImpl: GetGalleriesUseCaseImpl,
    private val getSimilarMoviesUseCaseImpl: GetSimilarMoviesUseCaseImpl,
    private val saveFavouriteMovieUseCase: SaveFavouriteMovieUseCaseImpl,
    private val saveSeenMovieUseCase: SaveSeenMovieUseCaseImpl
) : ViewModel() {

    private val _state = MutableStateFlow<com.jax.movies.presentation.home.movie.MovieDetailState>(
        com.jax.movies.presentation.home.movie.MovieDetailState.Initial)
    val state: StateFlow<com.jax.movies.presentation.home.movie.MovieDetailState> = _state.asStateFlow()

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
                    saveSeenMovieUseCase(intent.movie)
                }
            }

            is MovieScreenIntent.OnLickClick -> {
                viewModelScope.launch {
                    saveFavouriteMovieUseCase(intent.movie)
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
                fetchDetailInfo(action.movie)
            }
        }
    }

    private fun fetchDetailInfo(movieF: Movie) {
        _state.value = com.jax.movies.presentation.home.movie.MovieDetailState.Loading
        val movieDeferred: Deferred<Movie> = viewModelScope.async {
            var finalMovie = movie
            Log.d("dsdasdasdasdasssssssssssssssssssssssssssssssssssssssss", movie.id.toString())
            getDetailMovieUseCaseImpl(movie.id).first { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = com.jax.movies.presentation.home.movie.MovieDetailState.Error(result.message.toString())
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
                        _state.value = com.jax.movies.presentation.home.movie.MovieDetailState.Error(result.message.toString())
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
                        _state.value = com.jax.movies.presentation.home.movie.MovieDetailState.Error(result.message.toString())
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
                        _state.value = com.jax.movies.presentation.home.movie.MovieDetailState.Error(result.message.toString())
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
        viewModelScope.launch {
            val finalMovie = movieDeferred.await()
            val actorsList = actorsDeferred.await()
            val galleriesList = galleriesDeferred.await()
            val similarMoviesList = similarMoviesDeferred.await()
            val filmCrew = getFilmCrew(actorsList)
            _state.value = com.jax.movies.presentation.home.movie.MovieDetailState.Success(
                movie = finalMovie,
                actors = actorsList,
                filmCrew = filmCrew,
                gallery = galleriesList,
                similarMovies = similarMoviesList
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
@Composable
fun MovieContent(
    movieDetailViewModel: MovieDetailViewModel,
    movie: Movie
) {
    val state by movieDetailViewModel.state.collectAsStateWithLifecycle()
    when (val currentState = state) {
        is com.jax.movies.presentation.home.movie.MovieDetailState.Initial -> {}
        is com.jax.movies.presentation.home.movie.MovieDetailState.Loading -> com.jax.movies.presentation.components.LoadingScreen()
        is com.jax.movies.presentation.home.movie.MovieDetailState.Error -> com.jax.movies.presentation.components.ErrorScreen(
            errorMessage = currentState.message
        )
        is com.jax.movies.presentation.home.movie.MovieDetailState.Success -> {
            MainContent(
                movie = currentState.movie,
                onGalleryClick = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.MovieScreenNavigationIntent.OnGalleryClick(it))
                },
                onActorClick = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.MovieScreenNavigationIntent.OnActorClick(it))
                },
                onMovieClick = {
                    movieDetailViewModel.handleIntent(
                        MovieScreenIntent.MovieScreenNavigationIntent.OnMovieClick(
                            fromMovie = movie,
                            toMovie = it
                        )
                    )
                },
                onBackClicked = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.MovieScreenNavigationIntent.OnBackClicked(it))
                },
                onLikeClicked = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnLickClick(it))
                },
                onFavouriteClicked = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnFavouriteClick(it))
                },
                onShareClicked = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnShareClick)
                },
                onBlindEyeClicked = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnBlindEyeClick)
                },
                onMoreClicked = {
                    movieDetailViewModel.handleIntent(MovieScreenIntent.OnMoreClick)
                },
                galleries = currentState.gallery,
                actors = currentState.actors,
                filmCrew = currentState.filmCrew,
                similarMovies = currentState.similarMovies
            )
        }
    }
    LaunchedEffect(key1 = movie) {
        movieDetailViewModel.handleAction(MovieScreenAction.FetchMovieDetailInfo(movie))
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun MainContent(
    onGalleryClick: (Movie) -> Unit,
    onActorClick: (Actor) -> Unit,
    onMovieClick: (Movie) -> Unit,
    onBackClicked: (Movie) -> Unit,
    onLikeClicked: (Movie) -> Unit,
    onFavouriteClicked: (Movie) -> Unit,
    onShareClicked: () -> Unit,
    onBlindEyeClicked: () -> Unit,
    onMoreClicked: () -> Unit,
    movie: Movie,
    galleries: List<GalleryImage>,
    actors: List<Actor>,
    filmCrew: List<Actor>,
    similarMovies: List<Movie>,
    modifier: Modifier = Modifier
) {
    val actorsId = actors.map { it.actorId }
    Log.d("dasdasdsa", actorsId.toString())

    Scaffold(
        topBar = {
            com.jax.movies.presentation.components.MyTopAppBar(
                onNavClick = { onBackClicked(movie) },
                navIcon = R.drawable.icon_back,
                title = ""
            )
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(40.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            item {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(600.dp)
                ) {
                    FetchedImage(
                        linkToImage = movie.posterUrl,
                        modifierForParent = Modifier
                            .fillMaxWidth()
                            .matchParentSize(),
                    )
                    TitleSection(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        movie = movie,
                        onLikeClicked = onLikeClicked,
                        onFavouriteClicked = onFavouriteClicked,
                        onShareClicked = onShareClicked,
                        onBlindEyeClicked = onBlindEyeClicked,
                        onMoreClicked = onMoreClicked
                    )
                }
            }
            item {
                MainDescriptionWithSubDescription(
                    mainDescription = movie.description,
                    subDescription = movie.shortDescription,
                    modifier = Modifier
                        .padding(horizontal = 26.dp)
                )
            }
            item {
                ActorsSection(
                    title = "В фильме снимались",
                    countOther = actors.size,
                    itemInColumn = 2,
                    actors = actors,
                    onActorClick = onActorClick,
                    onTitleClick = {},
                    modifier = Modifier.padding(horizontal = 26.dp)
                )
            }
            item {
                ActorsSection(
                    title = "Над фильмом работали",
                    countOther = filmCrew.size,
                    itemInColumn = 2,
                    actors = filmCrew,
                    onActorClick = onActorClick,
                    onTitleClick = {},
                    modifier = Modifier.padding(horizontal = 26.dp)
                )
            }
            item {
                GallerySection(
                    count = galleries.size,
                    galleries = galleries,
                    onGalleryClick = { onGalleryClick(movie) },
                    modifier = Modifier.padding(horizontal = 26.dp)
                )
            }
            item {
                com.jax.movies.presentation.components.RelatedMoviesSection(
                    countOrAll = similarMovies.size.toString(),
                    relatedMovies = similarMovies,
                    onMovieClick = onMovieClick,
                    modifier = Modifier
                        .padding(horizontal = 26.dp)
                )
            }
        }
    }
}

@Composable
private fun TitleSection(
    movie: Movie,
    onLikeClicked: (Movie) -> Unit,
    onFavouriteClicked: (Movie) -> Unit,
    onShareClicked: () -> Unit,
    onBlindEyeClicked: () -> Unit,
    onMoreClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "${movie.ratingKp} ${movie.name}",
            color = Color(0xFFB5B5C9),
            modifier = Modifier.padding(top = 40.dp)
        )
        Row {
            Text(text = "${movie.year}", color = Color(0xFFB5B5C9))
            movie.genres.forEachIndexed { index, genre ->
                if (index < movie.genres.size - 1) Text(text = ",")
                Text(text = genre.genre, color = Color(0xFFB5B5C9))
            }
        }
        Row {
            movie.countries.forEachIndexed { index, country ->
                if (index < movie.countries.size - 1) Text(text = ",")
                Text(text = country.country, color = Color(0xFFB5B5C9))
            }
            Text(text = "${movie.lengthMovie} ${movie.ageLimit}")
        }
        Row {
            IconButton(onClick = {onLikeClicked(movie)}) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_liked),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(onClick = {onFavouriteClicked(movie)}) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_favourite),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(onClick = onBlindEyeClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_blind_eye),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(onClick = onShareClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_share),
                    contentDescription = "",
                    tint = Color.White
                )
            }
            IconButton(onClick = onMoreClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_more),
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun MainDescriptionWithSubDescription(
    mainDescription: String,
    subDescription: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = mainDescription,
            fontWeight = FontWeight.W700,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = subDescription,
            fontWeight = FontWeight.W400,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            maxLines = 5,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ActorsSection(
    onTitleClick: () -> Unit,
    onActorClick: (Actor) -> Unit,
    title: String,
    countOther: Int,
    itemInColumn: Int,
    actors: List<Actor>,
    modifier: Modifier = Modifier
) {
    Column {
        StepTitle(
            title = title,
            countOrOther = countOther.toString(),
            modifier = modifier,
            onTitleClick = onTitleClick
        )
        LazyHorizontalGrid(
            rows = GridCells.Fixed(itemInColumn),
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            items(actors) { actor ->
                com.jax.movies.presentation.components.ActorItem(
                    actor = actor,
                    onActorClick = onActorClick,
                    modifierForParent = Modifier.size(width = 207.dp, height = 68.dp)
                )
            }
        }
    }
}

@Composable
private fun GallerySection(
    onGalleryClick: () -> Unit,
    count: Int,
    galleries: List<GalleryImage>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        StepTitle(
            title = "Галерея",
            countOrOther = count.toString(),
            modifier = modifier,
            onTitleClick = onGalleryClick
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            galleries.forEach { galleryImage ->
                item {
                    GalleryCard(galleryImage = galleryImage)
                }
            }
        }
    }
}

@Composable
private fun GalleryCard(
    galleryImage: GalleryImage,
    modifier: Modifier = Modifier
) {
    FetchedImage(
        linkToImage = galleryImage.imageUrl,
        modifierForParent = modifier
            .width(192.dp)
            .height(108.dp)
            .clip(RoundedCornerShape(8.dp)),
        modifierForImage = Modifier.size(width = 192.dp, height = 108.dp)
    )
}
sealed class MovieScreenAction {
    data class FetchMovieDetailInfo(val movie: Movie) : MovieScreenAction()
}
sealed class MovieScreenIntent {
    sealed class MovieScreenNavigationIntent : MovieScreenIntent() {
        data class OnGalleryClick(val movie: Movie) : MovieScreenNavigationIntent()
        data class OnBackClicked(val movie: Movie) : MovieScreenNavigationIntent()
        data class OnActorClick(val actor: Actor) : MovieScreenNavigationIntent()
        data class OnMovieClick(val fromMovie: Movie, val toMovie: Movie) : MovieScreenNavigationIntent()
        data object Default : MovieScreenIntent()
    }

    data object OnShareClick : MovieScreenIntent()
    data object OnBlindEyeClick : MovieScreenIntent()
    data object OnMoreClick : MovieScreenIntent()
    data class OnLickClick(val movie: Movie) : MovieScreenIntent()
    data class OnFavouriteClick(val movie: Movie) : MovieScreenIntent()
}
@Suppress("UNCHECKED_CAST")
class MoviesDetailViewModel @AssistedInject constructor(
    @Assisted private val moviesType: MoviesType,
    private val getMovieCollectionUseCase: GetMovieCollectionUseCaseImpl
) : ViewModel() {

    private val _state: MutableStateFlow<MoviesScreenState> =
        MutableStateFlow(MoviesScreenState.Initial)
    val state: StateFlow<MoviesScreenState> = _state.asStateFlow()

    private val _moviesNavigationChannel = Channel<MoviesScreenIntent>()
    val moviesNavigationChannel = _moviesNavigationChannel.receiveAsFlow()

    fun handleIntent(intent: MoviesScreenIntent) {
        when (intent) {
            is MoviesScreenIntent.Default -> {}
            is MoviesScreenIntent.OnClickBack -> {
                viewModelScope.launch {
                    _moviesNavigationChannel.send(MoviesScreenIntent.OnClickBack)
                }
            }

            is MoviesScreenIntent.OnMovieClick -> {
                viewModelScope.launch {
                    _moviesNavigationChannel.send(MoviesScreenIntent.OnMovieClick(intent.movie))
                }
            }
        }
    }

    fun handleAction(action: MoviesScreenAction) {
        when (action) {
            is MoviesScreenAction.FetchMoviesDetailInfo -> {
                fetchMoviesDetail(moviesType)
            }
        }
    }

    private fun fetchMoviesDetail(type: MoviesType) {
        Log.d("MoviesDetailViewModel", "Fetching movies for type: $type")
        _state.value = MoviesScreenState.Loading
        viewModelScope.launch {
            getMovieCollectionUseCase(type).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            MoviesScreenState.Error(
                                result.message.localizedMessage ?: "Unknown error"
                            )
                        }
                    }

                    is Resource.Success -> {
                        Log.d("MoviesDetailViewModel", "Fetched movies: ${result.data}")
                        _state.update {
                            MoviesScreenState.Success(
                                movies = result.data,
                                type = type
                            )
                        }
                    }
                }
            }
        }
    }

    @AssistedFactory
    interface MoviesDetailFactory {
        fun create(moviesType: MoviesType): MoviesDetailViewModel
    }

    companion object{
        fun provideMoviesViewModel(
            moviesType: MoviesType,
            factory: MoviesDetailFactory
        ):ViewModelProvider.Factory = object:ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(moviesType) as T
            }
        }
    }
}
@Composable
fun MoviesDetailScreen(
    moviesDetailViewModel: MoviesDetailViewModel,
    type: MoviesType
) {
    val state by moviesDetailViewModel.state.collectAsStateWithLifecycle()

    when (val currentState = state) {
        is MoviesScreenState.Initial -> {}
        is MoviesScreenState.Loading -> com.jax.movies.presentation.components.LoadingScreen()
        is MoviesScreenState.Error -> com.jax.movies.presentation.components.ErrorScreen(
            currentState.message
        )
        is MoviesScreenState.Success -> {
            MainContent(
                movies = currentState.movies,
                moviesType = type,
                onMovieClick = {
                    moviesDetailViewModel.handleIntent(MoviesScreenIntent.OnMovieClick(it))
                },
                onClickBack = {
                    moviesDetailViewModel.handleIntent(MoviesScreenIntent.OnClickBack)
                }
            )
        }
    }
    LaunchedEffect(key1 = type) {
        moviesDetailViewModel.handleAction(MoviesScreenAction.FetchMoviesDetailInfo(type))
    }
}

@Composable
private fun MainContent(
    movies: List<Movie>,
    moviesType: MoviesType,
    onClickBack: () -> Unit,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchTopAppBar(onClickBack = { onClickBack() }, text = moviesType.name)
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = WindowInsets.navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding()
                )

        ) {
            items(movies) { movie ->
                com.jax.movies.presentation.components.MovieItem(
                    onMovieClick = { onMovieClick(movie) },
                    movie = movie
                )
            }
        }
    }
}

@Composable
private fun SearchTopAppBar(
    onClickBack: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(
                top = WindowInsets.statusBars
                    .asPaddingValues()
                    .calculateTopPadding()
            )
            .padding(16.dp)
    ) {
        IconButton(onClick = onClickBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.go_back),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = text,
            fontWeight = FontWeight.W900,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

sealed class MoviesScreenAction {
    data class FetchMoviesDetailInfo(val movieType: MoviesType) : MoviesScreenAction()
}

sealed class MoviesScreenIntent {
    data object Default : MoviesScreenIntent()
    data object OnClickBack : MoviesScreenIntent()
    data class OnMovieClick(val movie: Movie) : MoviesScreenIntent()
}
sealed class MoviesScreenState {
    data object Initial : MoviesScreenState()
    data object Loading : MoviesScreenState()
    data class Error(val message: String) : MoviesScreenState()
    data class Success(val movies: List<Movie>, val type: MoviesType) : MoviesScreenState()
}
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
                Box(modifier = Modifier.fillMaxSize()) { com.jax.movies.navigation.root.RootNavGraph() }
            }
            */
/* lifecycleScope.launch {
                 val responseDto = MoviesApiFactory.apiService.searchByQuery("s")
                 if (responseDto.isSuccessful) {
                     Log.d("dsadasdsadsadasdsa", "onCreate: ${responseDto.body()?.films}")
                 }
             }*//*

        }
    }
}
data class OnBoardingPageItem(
    val title: String,
    @DrawableRes val imageId: Int,
){
    companion object{
        val onboardingPages = listOf(
            OnBoardingPageItem(
                imageId = R.drawable.wfh_1,
                title = "Узнавай\nо премьерах",
            ),
            OnBoardingPageItem(
                imageId = R.drawable.wfh_2,
                title = "Создавай\nколлекции",
            ),
            OnBoardingPageItem(
                imageId = R.drawable.wfh_3,
                title = "Делись\nс друзьями",
            )
        )
    }
}
@SuppressLint("StateFlowValueCalledInComposition")
@ExperimentalFoundationApi
@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel
) {
    */
/*val state = viewModel.screenState
    when (val currentState = state.value) {
        is OnBoardingScreenState.Initial -> {

        }

        is OnBoardingScreenState.Error -> {
            ErrorScreen(currentState.message)
        }

        is OnBoardingScreenState.Success -> {
            OnBoardingMainContent(
                onFinishedClick = {
                    viewModel.handleIntent(OnBoardingScreenIntent.OnFinishClicked)
                }
            )
        }
    }
    LaunchedEffect(viewModel) {
        viewModel.handleAction(OnBoardingScreenAction.GetIsEnteredBeforeAction)
    }*//*

    OnBoardingMainContent(
        onFinishedClick = {
            viewModel.handleIntent(OnBoardingScreenIntent.OnFinishClicked)
        }
    )
}

@Composable
private fun OnBoardingMainContent(
    onFinishedClick: () -> Unit
) {
    val pagerState = rememberPagerState { onboardingPages.size }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(26.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Skillcinema",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage < onboardingPages.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            onFinishedClick()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    "Пропустить",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        HorizontalPager(state = pagerState) { page ->
            OnBoardingPageContent(onboardingPages[page])
        }

        DotsIndicator(
            totalDots = onboardingPages.size,
            selectedIndex = pagerState.currentPage
        )
    }
}

@Composable
private fun OnBoardingPageContent(
    page: com.jax.movies.presentation.onboarding.OnBoardingPageItem
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = page.imageId),
            contentDescription = "Page 1",
            modifier = Modifier.size(360.dp, 270.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = page.title,
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int, selectedIndex: Int
) {
    Row(
        modifier = Modifier.padding(bottom = 90.dp)
    ) {
        for (i in 0 until totalDots) {
            val color = if (i == selectedIndex) Color.Black else Color.Gray
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .padding(4.dp)
                    .background(color = color, shape = CircleShape)
            )
        }
    }
}
sealed class OnBoardingScreenAction {
    data object GetIsEnteredBeforeAction:OnBoardingScreenAction()
}
sealed class OnBoardingScreenIntent {
    data object Default : OnBoardingScreenIntent()
    data object OnFinishClicked : OnBoardingScreenIntent()
}
sealed class OnBoardingScreenState {
    data object Initial : OnBoardingScreenState()
    data class Success(val value: Boolean) : OnBoardingScreenState()
    data class Error(val message: String) : OnBoardingScreenState()
}
@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val getIsEnteredBeforeValueUseCase: GetIsEnteredBeforeValueUseCaseImpl
) : ViewModel() {
    private val _onBoardingNavigationChannel = Channel<OnBoardingScreenIntent>()
    val onBoardingNavigationChannel = _onBoardingNavigationChannel.receiveAsFlow()

    private val _screenState =
        MutableStateFlow<com.jax.movies.presentation.onboarding.OnBoardingScreenState>(com.jax.movies.presentation.onboarding.OnBoardingScreenState.Initial)
    val screenState = _screenState.asStateFlow()

    fun handleIntent(intent: OnBoardingScreenIntent) {
        when (intent) {
            is OnBoardingScreenIntent.OnFinishClicked -> {
                viewModelScope.launch {
                    _onBoardingNavigationChannel.send(intent)
                }
            }

            OnBoardingScreenIntent.Default -> {}
        }
    }

    fun handleAction(action: com.jax.movies.presentation.onboarding.OnBoardingScreenAction) {
        when (action) {
            com.jax.movies.presentation.onboarding.OnBoardingScreenAction.GetIsEnteredBeforeAction -> {
                viewModelScope.launch {
                    getIsEnteredBeforeValueUseCase().collect { response ->
                        _screenState.value = when (response) {
                            is Resource.Error -> com.jax.movies.presentation.onboarding.OnBoardingScreenState.Error(response.message.toString())
                            is Resource.Success -> com.jax.movies.presentation.onboarding.OnBoardingScreenState.Success(response.data)
                        }
                    }
                }
            }
        }
    }
}

data class CollectionItem(
    val title: String,
    val collectionTypeIcon: Int,
    val count: Int,
    val default: Boolean = true
)
@Composable
fun ProfileScreen(
    profileScreenViewModel: ProfileScreenViewModel,
    currentRoute: String
) {
    val state by profileScreenViewModel.state.observeAsState(ProfileScreenState.Initial)

    when (val currentState = state) {
        is ProfileScreenState.Initial -> {
            Text("Loading...")
            Log.d("dasdasdasdasdsadas", "ProfileScreen: Initial")
        }
        is ProfileScreenState.Error -> com.jax.movies.presentation.components.ErrorScreen(
            currentState.message
        )
        is ProfileScreenState.Success -> {
            ProfileMainContent(
                currentRoute = currentRoute,
                onSearchClick = {
                    profileScreenViewModel.handleIntent(ProfileScreenIntent.OnSearchClick)
                },
                onHomeClicked = {
                    profileScreenViewModel.handleIntent(ProfileScreenIntent.OnHomeClick)
                },
                onMovieClick = {
                    profileScreenViewModel.handleIntent(ProfileScreenIntent.OnMovieClick(it))
                },
                seenMovies = currentState.seenMovies,
                interestedMovies = currentState.favouriteMovies,
                collection = defaultCollectionTypes,
                onCollectionClick = {},
                deleteMoviesIconClicked = {}
            )
        }
    }
}


@Composable
private fun ProfileMainContent(
    currentRoute: String,
    onSearchClick: () -> Unit,
    onHomeClicked: () -> Unit,
    onMovieClick: (Movie) -> Unit,
    seenMovies: List<Movie>,
    interestedMovies: List<Movie>,
    collection: List<CollectionItem>,
    onCollectionClick: (CollectionItem) -> Unit,
    deleteMoviesIconClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        bottomBar = {
            com.jax.movies.presentation.components.BottomNavigationBar(
                currentRoute = currentRoute,
                onHomeClick = onHomeClicked,
                onSearchClick = onSearchClick,
                onProfileClick = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 26.dp)
                .verticalScroll(rememberScrollState())
        ) {

            com.jax.movies.presentation.components.RelatedMoviesSection(
                title = "Посмотрено",
                onMovieClick = onMovieClick,
                countOrAll = seenMovies.size.toString(),
                relatedMovies = seenMovies,
                deleteMoviesIcon = true,
                deleteMoviesIconClicked = deleteMoviesIconClicked,
                modifier = modifier
            )
            */
/*   CollectionComponent(
                   collection = collection,
                   onCollectionClick = onCollectionClick,
                   onAddClicked = {}
               )*//*

            com.jax.movies.presentation.components.RelatedMoviesSection(
                title = "Вам было интересно",
                onMovieClick = onMovieClick,
                countOrAll = interestedMovies.size.toString(),
                relatedMovies = interestedMovies,
                deleteMoviesIcon = true,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun CollectionComponent(
    collection: List<CollectionItem>,
    onCollectionClick: (CollectionItem) -> Unit,
    onAddClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Коллекции",
            fontWeight = FontWeight.W600,
            fontSize = 18.sp,
            color = Color(0xFF272727)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onAddClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.icons_add),
                    contentDescription = "add"
                )
            }
            Text(
                text = "Создать свою коллекцию",
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                color = Color(0xFF272727),
                modifier = Modifier.weight(1f)
            )
        }
        CollectionSection(
            collection = collection,
            onCollectionClick = onCollectionClick
        )
    }
}

@Composable
private fun CollectionSection(
    collection: List<CollectionItem>,
    onCollectionClick: (CollectionItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(collection) {
            CollectionTypeBox(
                collectionItem = it,
                onCollectionClick = onCollectionClick
            )
        }
    }
}

@Composable
private fun CollectionTypeBox(
    onCollectionClick: (CollectionItem) -> Unit,
    collectionItem: CollectionItem,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .size(146.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color(0xFF000000)
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(22.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = { onCollectionClick(collectionItem) }) {
                Icon(
                    painter = painterResource(id = collectionItem.collectionTypeIcon),
                    contentDescription = collectionItem.title,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = collectionItem.title,
                fontWeight = FontWeight.W400,
                fontSize = 12.sp,
                color = Color(0xFF272727)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF3D3BFF))
                    .padding(4.dp)
            ) {
                Text(
                    text = collectionItem.count.toString(),
                    fontWeight = FontWeight.W500,
                    fontSize = 8.sp,
                    color = Color(0xFFFFFFFF),
                    modifier = Modifier
                        .width(20.dp)
                        .height(10.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

sealed class ProfileScreenIntent {
    data object Default:ProfileScreenIntent()
    data object OnHomeClick:ProfileScreenIntent()
    data object OnSearchClick : ProfileScreenIntent()
    data class OnMovieClick(val movie: Movie) : ProfileScreenIntent()
}
sealed class ProfileScreenState {
    data class Success(
        val favouriteMovies: List<Movie>,
        val seenMovies: List<Movie>
    ) : ProfileScreenState()

    data class Error(val message: String) : ProfileScreenState()
    data object Initial : ProfileScreenState()
}
@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getFavouriteMoviesUseCase: GetFavouriteMoviesUseCaseImpl,
    private val getSeenMoviesUseCase: GetSeenMoviesUseCaseImpl
) : ViewModel() {

    private val _favouriteMovies = getFavouriteMoviesUseCase()
    private val _seenMovies = getSeenMoviesUseCase()

    private val _state = combine(_favouriteMovies, _seenMovies) { favouriteMovies, seenMovies ->
        ProfileScreenState.Success(favouriteMovies, seenMovies)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ProfileScreenState.Initial)

    val state = _state.asLiveData()

    private val _profileNavigationChannel = Channel<ProfileScreenIntent>()
    val profileNavigationChannel= _profileNavigationChannel.receiveAsFlow()
    fun handleIntent(intent:ProfileScreenIntent){
        when(intent){
            is ProfileScreenIntent.Default -> {}
            ProfileScreenIntent.OnHomeClick -> {
                viewModelScope.launch {
                    _profileNavigationChannel.send(ProfileScreenIntent.OnHomeClick)
                }
            }
            is ProfileScreenIntent.OnSearchClick -> {
                viewModelScope.launch {
                    _profileNavigationChannel.send(ProfileScreenIntent.OnSearchClick)
                }
            }

            is ProfileScreenIntent.OnMovieClick -> {
                viewModelScope.launch {
                    _profileNavigationChannel.send(ProfileScreenIntent.OnMovieClick(intent.movie))
                }
            }
        }
    }
}
@Composable
fun CountryScreen(
    onClickBack: () -> Unit,
    onChooseType: (String) -> Unit,
) {
    com.jax.movies.presentation.components.SortTypeSection(
        topBarTitle = "Страна",
        searchBarTitle = "Введите страну",
        onClickBack = onClickBack,
        onChooseType = onChooseType,
        types = defaultCountries
    )
}
@Composable
fun GenreScreen(
    onClickBack: () -> Unit,
    onChooseType: (String) -> Unit
) {
    com.jax.movies.presentation.components.SortTypeSection(
        types = defaultGenres,
        topBarTitle = "Жанр",
        searchBarTitle = "Введите жанр",
        onChooseType = onChooseType,
        onClickBack = onClickBack,
    )
}
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    currentRoute: String
) {
    val state by searchViewModel.screenState.collectAsStateWithLifecycle()

    when (val currentState = state) {

        is SearchScreenState.Error -> {
            SearchScreenNotFound()
        }

        is SearchScreenState.Loading -> {
            com.jax.movies.presentation.components.LoadingScreen()
        }

        is SearchScreenState.Initial -> {
            com.jax.movies.presentation.components.SearchBar(
                trailingIcon1 = R.drawable.icon_devider,
                trailingIcon2 = R.drawable.icon_sort,
                onSearchClick = {
                    searchViewModel.handleIntent(SearchScreenIntent.OnValueChange(it))
                },
                onTrailingIcon2Click = {
                    searchViewModel.handleEvent(SearchScreenIntent.Event.OnFilterClick)
                }
            )
        }

        is SearchScreenState.Success -> {
            SearchScreenContent(
                onSearchClick = {
                    searchViewModel.handleIntent(SearchScreenIntent.OnValueChange(it))
                },
                onMovieClick = {
                    searchViewModel.handleEvent(SearchScreenIntent.Event.OnMovieClick(it))
                },
                onProfileClick = {
                    searchViewModel.handleEvent(SearchScreenIntent.Event.OnProfileClick)
                },
                onHomeClick = {
                    searchViewModel.handleEvent(SearchScreenIntent.Event.OnHomeClick)
                },
                currentRoute = currentRoute,
                movies = currentState.movies
            )
        }
    }
}

@Composable
private fun SearchScreenContent(
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    currentRoute: String,
    onMovieClick: (Movie) -> Unit,
    onSearchClick: (String) -> Unit,
    movies: List<Movie>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        bottomBar = {
            com.jax.movies.presentation.components.BottomNavigationBar(
                currentRoute = currentRoute,
                onHomeClick = onHomeClick,
                onProfileClick = onProfileClick,
                onSearchClick = {},
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            com.jax.movies.presentation.components.SearchBar(
                leadingIcon = R.drawable.icon_search,
                placeHolderText = stringResource(R.string.searchbarPlaceHolder),
                trailingIcon1 = R.drawable.icon_devider,
                trailingIcon2 = R.drawable.icon_sort,
                onSearchClick = onSearchClick
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier
            ) {
                items(movies) { movie ->
                    com.jax.movies.presentation.components.MovieItem(
                        movie = movie,
                        onMovieClick = onMovieClick,
                        ratingPosition = Alignment.TopStart,
                        vertically = false,
                        modifier = Modifier.padding(start = 26.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchScreenNotFound() {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = stringResource(R.string.not_found_search_bar))
    }
}

@Composable
fun <T> FilterSection(
    chosenType: T,
    onChoose: (T) -> Unit,
    types: List<T>,
    title: String,
    chosenContainerColor: Color = Color(0xFF3D3BFF),
    unChosenContainerColor: Color = Color.White,
    chosenTextColor: Color = Color.White,
    unChosenTextColor: Color = Color.Black,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = title,
            color = Color(0xFF838390),
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(0xFF272727)
                    ),
                    shape = RoundedCornerShape(56.dp)
                )
                .padding(4.dp)
        ) {
            types.forEach { type ->
                val bgColor =
                    if (chosenType == type) chosenContainerColor else unChosenContainerColor
                val textColor = if (chosenType == type) chosenTextColor else unChosenTextColor
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(bgColor)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { onChoose(type) }
                ) {
                    Text(
                        text = type.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        color = textColor
                    )
                }
            }
        }
    }
}

@Composable
fun FilterTypesSection(
    onChoose: (FilterType) -> Unit,
    types: List<Pair<FilterType, String>> = defaultFilterTypes
) {
    Column {
        types.forEachIndexed { index, item ->
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(26.dp)
                        .clickable {
                            onChoose(item.first)
                        }
                ) {
                    Text(
                        text = item.first.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF272727)
                    )
                    Text(
                        text = item.second,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF838390)
                    )
                }
                if (index == types.size - 1) {
                    RatingSlider(
                        onChoose = { value ->
                            FilterType.RATING.updateDouble(value)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun RatingSlider(
    onChoose: (Double) -> Unit
) {
    var sliderValue by remember { mutableFloatStateOf(5f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = sliderValue,
            onValueChange = {
                sliderValue = it
                onChoose(it.toDouble())
            },
            valueRange = 1f..10f,
            steps = 8,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.Blue
            ),
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Value: ${sliderValue.toInt()}",
            color = Color.Blue
        )
    }
}
sealed interface SearchScreenIntent {
    data class OnValueChange(val value: String) : SearchScreenIntent
    sealed interface Event {
        data object Default : Event
        data object OnFilterClick : Event
        data object OnHomeClick : Event
        data object OnProfileClick : Event
        data class OnMovieClick(val movie: Movie) : Event
    }
}
sealed interface SearchScreenState {
    data object Initial : SearchScreenState
    data object Loading : SearchScreenState
    data class Error(val message: String) : SearchScreenState
    data class Success(val movies: List<Movie>) : SearchScreenState
}
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
@Composable
fun PeriodScreen(
    onClickBack:()->Unit,
    onChoosePeriod:(String,String)->Unit
) {
    com.jax.movies.presentation.components.PeriodSection(
        onPeriodChooseClick = onChoosePeriod,
        onClickBack = onClickBack
    )
}
enum class FilterType(var double: Double? = null) {
    COUNTRY,
    GENRE,
    YEAR,
    RATING;

    fun updateDouble(value: Double) {
        this.double = value
    }
}
@Composable
fun SearchSettingScreen(
    searchSettingViewModel: SearchSettingViewModel,
    country: String,
    genre: String,
    period: String
) {
    val state by searchSettingViewModel.screenState.collectAsStateWithLifecycle()
    FilterScreenContent(
        state = state,
        onBackClick = {
            searchSettingViewModel.handleEvent(SearchSettingScreenIntent.Event.OnBackClick)
        },
        onFilterTypeChoose = {
            when (it) {
                FilterType.COUNTRY -> {
                    searchSettingViewModel.handleEvent(SearchSettingScreenIntent.Event.OnCountryClick)
                }

                FilterType.GENRE -> {
                    searchSettingViewModel.handleEvent(SearchSettingScreenIntent.Event.OnGenreClick)
                }

                FilterType.YEAR -> {
                    searchSettingViewModel.handleEvent(SearchSettingScreenIntent.Event.OnPeriodClick)
                }

                FilterType.RATING -> {

                }
            }
        },
        onShowTypeChoose = {},
        onSortingTypeChoose = {}
    )
    LaunchedEffect(Unit) {
        searchSettingViewModel.handleIntent(SearchSettingScreenIntent.OnCountryChange(country))
        searchSettingViewModel.handleIntent(SearchSettingScreenIntent.OnPeriodChange(period))
        searchSettingViewModel.handleIntent(SearchSettingScreenIntent.OnGenreChange(genre))
    }
}

@Composable
private fun FilterScreenContent(
    onBackClick: () -> Unit,
    onShowTypeChoose: (ShowType) -> Unit,
    onSortingTypeChoose: (SortingType) -> Unit,
    onFilterTypeChoose: (FilterType) -> Unit,
    state: SearchSettingScreenState
) {
    Scaffold(
        topBar = {
            com.jax.movies.presentation.components.MyTopAppBar(
                onNavClick = onBackClick,
                navIcon = R.drawable.icon_back,
                title = "Настройки поиска"
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            com.jax.movies.presentation.search.main.FilterSection(
                title = stringResource(R.string.show),
                types = defaultMovieTypes,
                onChoose = {
                    val showType = it as ShowType
                    onShowTypeChoose(showType)
                },
                chosenType = state.showType
            )
            com.jax.movies.presentation.search.main.FilterTypesSection(
                onChoose = onFilterTypeChoose
            )
            com.jax.movies.presentation.search.main.FilterSection(
                title = stringResource(R.string.sort),
                types = defaultMovieTypes,
                onChoose = {
                    val sortingType = it as SortingType
                    onSortingTypeChoose(sortingType)
                },
                chosenType = state.sortingType
            )
        }
    }
}
sealed interface SearchSettingScreenIntent {
    data class OnRatingChange(val rating: Double) : SearchSettingScreenIntent
    data class OnCountryChange(val country: String) : SearchSettingScreenIntent
    data class OnGenreChange(val genre: String) : SearchSettingScreenIntent
    data class OnPeriodChange(val period: String) : SearchSettingScreenIntent
    data class OnSortingTypeChoose(val sortingType: SortingType) : SearchSettingScreenIntent
    data class OnShowTypeChoose(val showType: ShowType) : SearchSettingScreenIntent
    data class OnFilterTypeChoose(val filterType: FilterType) : SearchSettingScreenIntent
    sealed interface Event {
        data object Default : Event
        data object OnBackClick : Event
        data object OnCountryClick : Event
        data object OnGenreClick : Event
        data object OnPeriodClick : Event
    }
}
data class SearchSettingScreenState(
    val country: String = "",
    val genre: String = "",
    val period: String = "",
    val rating: Double = 0.0,
    val sortingType: SortingType = SortingType.DATE,
    val showType: ShowType = ShowType.DEFAULT,
    val filterType: FilterType = FilterType.YEAR
)
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

enum class ShowType {
    DEFAULT,
    FILMS,
    SERIALS
}
enum class SortingType {
    DATE,
    POPULARITY,
    RATING
}
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val Blue1 = Color(0xFF3D3BFF)
private val DarkColorScheme = darkColorScheme(
    primary = com.jax.movies.presentation.theme.Purple80,
    secondary = com.jax.movies.presentation.theme.PurpleGrey80,
    tertiary = com.jax.movies.presentation.theme.Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = com.jax.movies.presentation.theme.Purple40,
    secondary = com.jax.movies.presentation.theme.PurpleGrey40,
    tertiary = com.jax.movies.presentation.theme.Pink40

    */
\



/* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    *//*

)


@Composable
fun MoviesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
object Constants {

    const val FILM_API_BASE_URL = "https://kinopoiskapiunofficial.tech/api/"
    const val FAVOURITE_MOVIES_TABLE = "FavouriteMoviesTable"
    const val SEEN_MOVIES_TABLE = "SeenMoviesTable"
    const val MOVIE_DATABASE = "MOVIE_DATABASE"
}
object DefaultLists {
    val yearsList = listOf(
        "1998",
        "1999",
        "2000",
        "2001",
        "2002",
        "2003",
        "2004",
        "2005",
        "2006",
        "2007",
        "2008",
        "2009",
    )

    val defaultGenres = listOf(
        "Комедия",
        "Мелодрама",
        "Боевик",
        "Вестерн",
        "Драма",
    )
    val defaultCountries = listOf(
        "Россия",
        "Великобритания",
        "Германия",
        "Сша",
        "Франция",
    )

    val defaultMovieTypes = listOf(
        "Все",
        "Фильмы",
        "Сериалы",
    )
    val defaultSortTypes = listOf(
        "Дата",
        "Популярность",
        "Рейтинг",
    )
    val defaultFilterTypes = listOf(
        FilterType.COUNTRY to "Россия",
        FilterType.GENRE to "Комедия",
        FilterType.YEAR to "с 1998 до 2017",
        FilterType.RATING to "любой"
    )
    val defaultCollectionTypes = listOf(
        CollectionItem(
            title ="Любимые",
            collectionTypeIcon = R.drawable.icon_liked,
            count = 0
        ),
        CollectionItem(
            title = "Хочу посмотреть",
            collectionTypeIcon = R.drawable.icon_favourite,
            count = 0
        ),
        CollectionItem(
            title = "Хочу посмотреть",
            collectionTypeIcon = R.drawable.icon_favourite,
            count = 0
        ),
        CollectionItem(
            title = "Хочу посмотреть",
            collectionTypeIcon = R.drawable.icon_favourite,
            count = 0
        ),
        CollectionItem(
            title = "Хочу посмотреть",
            collectionTypeIcon = R.drawable.icon_favourite,
            count = 0
        )
    )
}
fun ActorType.toPlayRole(): String {
    return when (this) {
        ActorType.WRITE -> "Играет писателя"
        ActorType.OPERATOR -> "Играет оператора"
        ActorType.EDITOR -> "Играет эдитора"
        ActorType.COMPOSER -> "Играет компоузера"
        ActorType.PRODUCER_USSR -> "Играет  юсср"
        ActorType.TRANSLATOR -> "Играет переводчика"
        ActorType.DIRECTOR -> "Играет директора"
        ActorType.DESIGN ->  "Играет дизайнера"
        ActorType.PRODUCER -> "Играет продюсера"
        ActorType.ACTOR -> "Играет саму себя"
        ActorType.VOICE_DIRECTOR -> "Играет войс продюсера"
        ActorType.UNKNOWN -> "Неизвестный роль"
    }
}

fun List<Movie>.checkRealCount(): Int {
    return this.filter {
        it.id.toInt() != -1
    }.size
}
sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: Throwable) : Resource<Nothing>()
}
@HiltAndroidApp
class MoviesApp : Application()

*/
