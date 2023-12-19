package com.digian.clean.features.movies.routing

/**
 * Class defining all possible screens in the app.
 */
sealed class Screen(val route: String) {

    companion object {
        fun fromRoute(route: String): Screen {
            return when (route) {
                MoviesList.route -> MoviesList
                MoviesDetail.route -> MoviesDetail
                else -> MoviesList
            }
        }
    }

    data object MoviesList : Screen("MoviesList")
    data object MoviesDetail : Screen("MoviesDetail")
}