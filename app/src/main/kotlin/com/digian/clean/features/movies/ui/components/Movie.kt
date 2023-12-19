package com.digian.clean.features.movies.ui.components
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digian.clean.features.movies.domain.entities.MovieEntity

@ExperimentalMaterialApi
@Composable
fun Movie(
    modifier: Modifier = Modifier,
    movieEntity: MovieEntity,
    onMovieClick: (MovieEntity) -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        ListItem(
            text = { Text(text = movieEntity.title, maxLines = 1) },
            modifier = Modifier.clickable {
                onMovieClick.invoke(movieEntity)
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
private fun NotePreview() {
    Movie(movieEntity = MovieEntity(1, 3456, "Lord of the Rings", "", emptyList(),"" ))
}