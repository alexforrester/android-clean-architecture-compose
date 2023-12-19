package com.digian.clean.features.movies.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.presentation.MoviesListViewModel

//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//private fun MoviesList(
//    movies: List<MoviesListViewModel>,
//    onMovieClick: (MovieEntity) -> Unit
//) {
//    LazyColumn {
//        items(count = movies.size) { moviesIndex ->
//            val movieEntity = movies[moviesIndex]
//            MovieEntity(
//                note = note,
//                onNoteClick = onNoteClick,
//                onNoteCheckedChange = onNoteCheckedChange,
//                isSelected = false
//            )
//
////            Note(
////                note = note,
////                onNoteClick = onNoteClick,
////                onNoteCheckedChange = onNoteCheckedChange,
////                isSelected = false
////            )
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun MoviesListPreview() {
//    NotesList(
//        notes = listOf(
//            NoteModel(1, "Note 1", "Content 1", null),
//            NoteModel(2, "Note 2", "Content 2", false),
//            NoteModel(3, "Note 3", "Content 3", true)
//        ),
//        onNoteCheckedChange = {},
//        onNoteClick = {}
//    )
//}
//
//@Composable
//private fun Movie(
//
//
//
//) {
//
