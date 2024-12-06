package com.jax.movies.data.repository

import android.util.Log
import com.jax.movies.data.mapper.MoviesMapper
import com.jax.movies.data.remote.api.MoviesApiService
import com.jax.movies.domain.entity.home.Country
import com.jax.movies.domain.entity.home.Genre
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.repository.SearchRepository
import com.jax.movies.presentation.search.main.SortBy
import com.jax.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: MoviesApiService,
    private val movieMapper: MoviesMapper
) : SearchRepository {
    override suspend fun searchQuery(query: String, sortBy: SortBy): Flow<Resource<List<Movie>>> {
        return flow {
            val response = apiService.searchByQuery(
                query = query,
                fromRating = sortBy.rating.toInt(),
                toRating = 10,
                yearFrom = sortBy.period.first,
                yearTo = sortBy.period.second,
                countries = sortBy.country.name.getCountryIdByName(),
                genres = sortBy.genre.name.getGenreIdByName(),
                order = sortBy.sort.name,
                type = sortBy.showType.name
            )
            if (response.isSuccessful) {
                val films = response.body()?.films
                if (films != null) {
                    val movies = films.map { movieMapper.movieDtoToEntity(it) }

                    Log.d("SearchRepositoryImpl", "NON Filtered movies: $movies")
                    Log.d("SearchRepositoryImpl", "Filtered movies: ${movies.filter(sortBy)}")
                    emit(Resource.Success(movies))
                } else {
                    emit(Resource.Error(Exception("Response body is null")))
                }
            } else {
                emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
            }
        }
    }

    override suspend fun getGenres(query: String): Flow<Resource<Genre>> {
        return flow {
            val response = apiService.getGenres()
            if (response.isSuccessful) {
                response.body()?.genres.let { genres ->
                    val genresList = genres?.map {
                        movieMapper.dtoGenreToEntity(it)
                    } ?: emptyList()
                    val genre = genresList.getGenreByQuery(query)
                    if(genre != null){
                        emit(Resource.Success(genre))
                    }else{
                        emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
                    }
                }
            } else {
                emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
            }
        }
    }

    override suspend fun getCountries(query: String): Flow<Resource<Country>> {
        return flow {
            val response = apiService.getCountries()
            if (response.isSuccessful) {
                response.body()?.countries.let { countries ->
                    val countriesList = countries?.map {
                        movieMapper.dtoCountryToEntity(it)
                    } ?: emptyList()
                    val country = countriesList.getCountryByQuery(query)
                    if(country != null){
                        emit(Resource.Success(country))
                    }else{
                        emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
                    }
                }
            } else {
                emit(Resource.Error(Exception("Error: ${response.code()} - ${response.message()}")))
            }
        }
    }

    private fun List<Movie>.filter(sortBy: SortBy): List<Movie> {
        return this.filter { movie ->
            movie.ratingKp >= sortBy.rating
        }.filter { movie ->
            movie.year in sortBy.period.first..sortBy.period.second
        }.filter { movie ->
            movie.countries.any { it.country == sortBy.country.name }
        }.filter { movie ->
            movie.genres.any { it.genre == sortBy.genre.name }
        }
    }

    private fun List<Country>.getCountryByQuery(query: String): Country? {
        val lowercaseQuery = query.toLowerCase()
        return this.firstOrNull { it.name.toLowerCase() == lowercaseQuery }
    }

    private fun List<Genre>.getGenreByQuery(query: String): Genre? {
        val lowercaseQuery = query.toLowerCase()
        return this.firstOrNull { it.name.toLowerCase() == lowercaseQuery }
    }


    private fun String.getCountryIdByName(): String {
        return when {
            this.contains("Россия", ignoreCase = true) -> 0
            this.contains("США", ignoreCase = true) -> 1
            this.contains("Швейцария", ignoreCase = true) -> 2
            this.contains("Франция", ignoreCase = true) -> 3
            this.contains("Польша", ignoreCase = true) -> 4
            this.contains("Великобритания", ignoreCase = true) -> 5
            this.contains("Швеция", ignoreCase = true) -> 6
            this.contains("Индия", ignoreCase = true) -> 7
            this.contains("Испания", ignoreCase = true) -> 8
            this.contains("Германия", ignoreCase = true) -> 9
            this.contains("Италия", ignoreCase = true) -> 10
            this.contains("Гонконг", ignoreCase = true) -> 11
            this.contains("Германия (ФРГ)", ignoreCase = true) -> 12
            this.contains("Австралия", ignoreCase = true) -> 13
            this.contains("Канада", ignoreCase = true) -> 14
            this.contains("Мексика", ignoreCase = true) -> 15
            this.contains("Япония", ignoreCase = true) -> 16
            this.contains("Дания", ignoreCase = true) -> 17
            this.contains("Чехия", ignoreCase = true) -> 18
            this.contains("Ирландия", ignoreCase = true) -> 19
            this.contains("Люксембург", ignoreCase = true) -> 20
            this.contains("Китай", ignoreCase = true) -> 21
            this.contains("Норвегия", ignoreCase = true) -> 22
            this.contains("Нидерланды", ignoreCase = true) -> 23
            this.contains("Аргентина", ignoreCase = true) -> 24
            this.contains("Финляндия", ignoreCase = true) -> 25
            this.contains("Босния и Герцеговина", ignoreCase = true) -> 26
            this.contains("Австрия", ignoreCase = true) -> 27
            this.contains("Тайвань", ignoreCase = true) -> 28
            this.contains("Новая Зеландия", ignoreCase = true) -> 29
            this.contains("Бразилия", ignoreCase = true) -> 30
            else -> 0
        }.toString()
    }

    private fun String.getGenreIdByName(): String {
        return when {
            this.contains("триллер", ignoreCase = true) -> 1
            this.contains("драма", ignoreCase = true) -> 2
            this.contains("криминал", ignoreCase = true) -> 3
            this.contains("мелодрама", ignoreCase = true) -> 4
            this.contains("детектив", ignoreCase = true) -> 5
            this.contains("фантастика", ignoreCase = true) -> 6
            this.contains("приключения", ignoreCase = true) -> 7
            this.contains("биография", ignoreCase = true) -> 8
            this.contains("фильм-нуар", ignoreCase = true) -> 9
            this.contains("вестерн", ignoreCase = true) -> 10
            this.contains("боевик", ignoreCase = true) -> 11
            this.contains("фэнтези", ignoreCase = true) -> 12
            this.contains("комедия", ignoreCase = true) -> 13
            this.contains("военный", ignoreCase = true) -> 14
            this.contains("история", ignoreCase = true) -> 15
            this.contains("музыка", ignoreCase = true) -> 16
            this.contains("ужасы", ignoreCase = true) -> 17
            this.contains("мультфильм", ignoreCase = true) -> 18
            this.contains("семейный", ignoreCase = true) -> 19
            this.contains("мюзикл", ignoreCase = true) -> 20
            this.contains("спорт", ignoreCase = true) -> 21
            this.contains("документальный", ignoreCase = true) -> 22
            this.contains("короткометражка", ignoreCase = true) -> 23
            this.contains("аниме", ignoreCase = true) -> 24
            this.isBlank() -> 25
            this.contains("новости", ignoreCase = true) -> 26
            this.contains("концерт", ignoreCase = true) -> 27
            this.contains("для взрослых", ignoreCase = true) -> 28
            this.contains("церемония", ignoreCase = true) -> 29
            this.contains("реальное ТВ", ignoreCase = true) -> 30
            this.contains("игра", ignoreCase = true) -> 31
            this.contains("ток-шоу", ignoreCase = true) -> 32
            this.contains("детский", ignoreCase = true) -> 33
            else -> 0
        }.toString()
    }
}