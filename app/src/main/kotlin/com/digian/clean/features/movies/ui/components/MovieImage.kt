package com.digian.clean.features.movies.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.digian.clean.features.movies.domain.entities.MovieEntity


@Composable
fun MovieImage(modifier: Modifier, movieEntity: MovieEntity?, imageUrl: String) {

    movieEntity?.run {
        AsyncImage(
            modifier = modifier,
            alignment = Alignment.TopEnd,
            model = imageUrl,
            contentDescription = null,
        )
    }
}


@Preview
@Composable
private fun MovieImagePreview() {
    MovieImage(
        modifier = Modifier,
        movieEntity = MovieEntity(1, 3456, "Lord of the Rings", "", emptyList(), ""),
        imageUrl = "https://image.tmdb.org/t/p/w400/rPdtLWNsZmAtoZl9PK7S2wE3qiS.jpg"
    )
}
