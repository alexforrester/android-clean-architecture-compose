package com.digian.clean.features.movies.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.presentation.MoviesListViewModelCompose
import com.digian.clean.features.movies.ui.components.Movie

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MoviesListScreen(
    modifier: Modifier = Modifier,
    viewModel: MoviesListViewModelCompose,
    onMovieClick: (MovieEntity) -> Unit,
) {
    val movies: State<List<MovieEntity>> = viewModel.movies.observeAsState(listOf())
    LoadMoviesList(viewModel, movies.value, onMovieClick)
    viewModel.loadMovies()
}

@Composable
fun LoadMoviesList(
    viewModel: MoviesListViewModelCompose,
    movies: List<MovieEntity>,
    onMovieClick: (MovieEntity) -> Unit
) {

    if (movies.isNotEmpty()) {
        MoviesList(
            movies = movies,
            onMovieClick = onMovieClick
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MoviesList(
    movies: List<MovieEntity>,
    onMovieClick: (MovieEntity) -> Unit,
) {
    LazyColumn {
        items(count = movies.size) { moviesIndex ->
            val movieEntity = movies[moviesIndex]
            Movie(
                movieEntity = movieEntity,
                onMovieClick = onMovieClick,
            )
        }
    }
}

@Preview
@Composable
private fun MoviesListPreview() {
    MoviesList(
        movies = listOf(
            MovieEntity(1, 234232, "Lord of the Rings", "", emptyList(), ""),
            MovieEntity(2, 43435, "Forrest Gump", "", emptyList(), ""),
            MovieEntity(1, 345253, "The Godfather", "", emptyList(), "")
        ), {}
    )
}
