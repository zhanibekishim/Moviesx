package com.jax.movies.data.repository

import com.jax.movies.data.local.database.FavouriteMoviesDao
import com.jax.movies.data.local.database.MovieCollectionDao
import com.jax.movies.data.local.database.SeenMoviesDao
import com.jax.movies.data.mapper.FilmsMapper
import com.jax.movies.domain.entity.home.Movie
import com.jax.movies.domain.repository.ProfileRepository
import com.jax.movies.presentation.components.MovieCollectionItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val favouriteMoviesDao: FavouriteMoviesDao,
    private val seenMoviesDao: SeenMoviesDao,
    private val movieCollectionDao: MovieCollectionDao,
    private val filmsMapper: FilmsMapper,
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

    override suspend fun deleteCollection(movieCollectionItem: MovieCollectionItem): Boolean {
        val item = movieCollectionDao.getMovieCollectionByName("some_name")

        movieCollectionDao.deleteMovieCollection(movieCollectionItem)
        return false
    }

    override suspend fun deleteSFavouriteMovies() {
        favouriteMoviesDao.deleteAllFavouriteMovies()
    }

    override suspend fun deleteSeenMovies() {
        seenMoviesDao.deleteAllSeenMovies()
    }

    override suspend fun deleteMoviesCollection() {
        movieCollectionDao.deleteAllFromMoviesCollection()
    }
}