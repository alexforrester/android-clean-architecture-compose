package com.digian.clean.features.movies.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.digian.clean.features.movies.domain.entities.GenreEntity
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.presentation.IMAGE_URL_AND_PATH
import com.digian.clean.features.movies.presentation.MovieDetailViewModelCompose


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailViewModelCompose
) {
    val movieEntity: State<MovieEntity?> = viewModel.movie.observeAsState()

    ConstraintLayout(modifier = modifier.padding(vertical = 6.dp, horizontal = 6.dp)) {
        // Create references for the composables to constrain
        val (title, body, genre, votes, image) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start)
                }
                .padding(8.dp, 0.dp),
            style = TextStyle(fontWeight = FontWeight(600), fontSize = 22.sp),
            text = movieEntity.value?.title ?: "",

            )
        Text(
            movieEntity.value?.shortOverview ?: "",
            Modifier
                .constrainAs(body) {
                    top.linkTo(title.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(image.start)
                    width = Dimension.preferredWrapContent
                }
                .padding(8.dp, 0.dp),
            style = TextStyle(fontSize = 22.sp),
        )
        Text(
            text = createGenreText(movieEntity.value?.genres ?: emptyList()),
            Modifier
                .constrainAs(genre) {
                    top.linkTo(body.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                }
                .padding(8.dp, 0.dp),
        )

        Text(
            modifier = Modifier
                .constrainAs(votes) {
                    top.linkTo(genre.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                }
                .padding(10.dp, 0.dp),
            style = TextStyle(fontWeight = FontWeight(600), fontSize = 16.sp),
            text = movieEntity.value?.voteCount.toString(),
        )

        DisplayMovie(
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(title.bottom)
                    linkTo(body.end, parent.end, bias = 1f)
                }
                .padding(8.dp, 0.dp), movieEntity = movieEntity.value
        )
    }
}

@Composable
fun DisplayMovie(modifier: Modifier, movieEntity: MovieEntity?) {

    movieEntity?.run {
        val posterImage = IMAGE_URL_AND_PATH.plus(movieEntity.imagePath)

        AsyncImage(
            modifier = modifier,
            alignment = Alignment.TopEnd,
            model = posterImage,
            contentDescription = null,
        )
    }

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





