package com.digian.clean.features.movies.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Scaffold
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
) {
    val movies: State<List<MovieEntity>> = viewModel.movies.observeAsState(listOf())
    LoadMoviesList(modifier, viewModel, movies.value)
    viewModel.loadMovies()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoadMoviesList(
    modifier: Modifier = Modifier,
    viewModel: MoviesListViewModelCompose,
    movies: List<MovieEntity>
) {

    Scaffold(
        content = {
            MoviesList(
                modifier = modifier,
                movies = movies,
                onMovieClick = {}
            )
        }
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MoviesList(
    modifier: Modifier = Modifier,
    movies: List<MovieEntity>,
    onMovieClick: (MovieEntity) -> Unit = {},
) {
    LazyColumn {
        items(count = movies.size) { moviesIndex ->
            val movieEntity = movies[moviesIndex]
            Movie(
                modifier = modifier,
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
        modifier = Modifier,
        movies = listOf(
            MovieEntity(1, 234232, "Lord of the Rings", "", emptyList(), ""),
            MovieEntity(2, 43435, "Forrest Gump", "", emptyList(), ""),
            MovieEntity(1, 345253, "The Godfather", "", emptyList(), "")
        )
    )
}
