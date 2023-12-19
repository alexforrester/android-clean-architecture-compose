package com.digian.clean.features.movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.tooling.preview.Preview
import com.digian.clean.R
import com.digian.clean.core.data.exception.Failures
import com.digian.clean.features.movies.presentation.MoviesListViewModel
import com.digian.clean.features.movies.theme.AndroidCleanArchitectureComposeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesActivityCompose : ComponentActivity() {


//    moviesListViewModel.movies.observe(viewLifecycleOwner,
//    Observer<List<MovieEntity>> { popularMovies ->
//        moviesListAdapter.data = popularMovies
//        moviesListAdapter.notifyDataSetChanged()
//    })
//
//    moviesListViewModel.failure.observe(viewLifecycleOwner,
//    Observer { failure ->
//        Toast.makeText(
//            activity,
//            getString(R.string.movie_list_loading_error).plus((failure as? Failures)?.exception?.message),
//            Toast.LENGTH_LONG
//        ).show()
//    })
//
//    moviesListViewModel.loadMovies()


    private val moviesListViewModel: MoviesListViewModel by viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidCleanArchitectureComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold (
                        topBar = {
                            TopAppBar(
                                colors = topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    titleContentColor = MaterialTheme.colorScheme.primary,
                                ),
                                title = {
                                    Text( text = getString(R.string.popular_movies))
                                }
                            )
                        },
                        content = { innerPadding ->
                        Greeting("Android", modifier = Modifier
                            .padding(innerPadding))
                    })
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidCleanArchitectureComposeTheme {
        Greeting("Android")
    }
}