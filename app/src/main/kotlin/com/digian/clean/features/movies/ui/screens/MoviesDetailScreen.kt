package com.digian.clean.features.movies.ui.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter
import com.digian.clean.features.movies.domain.entities.GenreEntity
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.presentation.IMAGE_URL_AND_PATH
import com.digian.clean.features.movies.presentation.MovieDetailViewModelCompose


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModelCompose,
    onNavigateBack: () -> Unit = {}
) {

    val movieEntity: State<MovieEntity?> = viewModel.movie.observeAsState()
    
    DisplayMovie(movieEntity = movieEntity.value)
    




}
@Composable
fun DisplayMovie(movieEntity: MovieEntity?) {

    movieEntity?.run {

        val posterImage = IMAGE_URL_AND_PATH.plus(movieEntity.imagePath)

        AsyncImage(
            model = posterImage,
            contentDescription = null,
        )

    }

}


@Composable
fun TitleText (modifier: Modifier, text: String) {
    OutlinedTextField(value = text, onValueChange = {}, modifier = modifier)
}




fun createGenreText(genreData: List<GenreEntity>): String {
    val genreNames = genreData.map { genre -> genre.name }

    if (genreData.isEmpty()) {
        return ""
    }

    var genresText = "GENRES: "

    genreNames.forEach { genre ->
        genresText += genre.plus(", ")
    }

    return genresText.trimEnd().substringBeforeLast(",")
}





