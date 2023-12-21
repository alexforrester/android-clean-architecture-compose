package com.digian.clean.features.movies.routing

/**
 * Class defining all possible screens in the app.
 */
sealed class Screen(val route: String) {
    data object MoviesList : Screen("MoviesList")
    data object MoviesDetail : Screen("MoviesDetail")
}