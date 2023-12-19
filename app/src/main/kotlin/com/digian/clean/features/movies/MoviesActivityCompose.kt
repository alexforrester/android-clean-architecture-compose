package com.digian.clean.features.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.digian.clean.R
import com.digian.clean.features.movies.presentation.MovieDetailViewModelCompose
import com.digian.clean.features.movies.presentation.MoviesListViewModelCompose
import com.digian.clean.features.movies.routing.Screen
import com.digian.clean.features.movies.theme.AndroidCleanArchitectureComposeTheme
import com.digian.clean.features.movies.ui.screens.MovieDetailScreen
import com.digian.clean.features.movies.ui.screens.MoviesListScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesActivityCompose : ComponentActivity() {

    private val moviesListViewModelCompose: MoviesListViewModelCompose by viewModel()
    private val movieDetailViewModelCompose: MovieDetailViewModelCompose by viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidCleanArchitectureComposeTheme {

                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                colors = topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    titleContentColor = MaterialTheme.colorScheme.primary,
                                ),
                                title = {
                                    Text(text = getString(R.string.popular_movies))
                                }
                            )
                        },
                        content = { innerPadding ->

                            AppScreens(
                                modifier = Modifier.padding(
                                    0.dp,
                                    innerPadding.calculateTopPadding(),
                                    0.dp,
                                    innerPadding.calculateBottomPadding()
                                ),
                                navController = navController,
                                moviesListViewModelCompose,
                                movieDetailViewModelCompose
                            )
                        })
                }
            }
        }
    }
}


@Composable
private fun AppScreens(
    modifier: Modifier,
    navController: NavHostController,
    moviesListViewModelCompose: MoviesListViewModelCompose,
    moviesDetailViewModelCompose: MovieDetailViewModelCompose,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MoviesList.route
    ) {
        composable(Screen.MoviesList.route) {
            MoviesListScreen(
                viewModel = moviesListViewModelCompose,
                onMovieClick = { movieEntity ->
                    moviesDetailViewModelCompose.loadMovie(movieEntity.id)
                    navController.navigate(Screen.MoviesDetail.route)
                }
            )
        }
        composable(Screen.MoviesDetail.route) {
            MovieDetailScreen(
                viewModel = moviesDetailViewModelCompose,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
