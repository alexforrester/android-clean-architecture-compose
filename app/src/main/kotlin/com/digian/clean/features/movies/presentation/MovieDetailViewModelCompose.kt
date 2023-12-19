package com.digian.clean.features.movies.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digian.clean.core.domain.exception.Failure
import com.digian.clean.core.domain.ports.UseCaseInputPort
import com.digian.clean.core.domain.ports.UseCaseOutputPort
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.digian.clean.features.movies.domain.usecases.MovieDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Alex Forrester on 23/04/2019.
 */
class MovieDetailViewModelCompose(val movieDetailUseCase: MovieDetailUseCase) : ViewModel() {

    val failure: MutableLiveData<Failure> = MutableLiveData()

    private val _movie: MutableLiveData<MovieEntity> = MutableLiveData()
    val movie: LiveData<MovieEntity> = _movie

    fun loadMovie(movieId : Int) {
        viewModelScope.launch {
            val useCaseOutputDeferred = async { getMovieDetail(movieId) }
            val useCaseOutput : UseCaseOutputPort<Failure, MovieEntity> = useCaseOutputDeferred.await()
            useCaseOutput.successOrError(::handleFailure, ::handleSuccess)
        }
    }

    private suspend fun getMovieDetail(movieId: Int) : UseCaseOutputPort<Failure,MovieEntity> =
        withContext(Dispatchers.IO) {
            movieDetailUseCase(UseCaseInputPort.Single(movieId))
        }

    private fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }

    private fun handleSuccess(movie: MovieEntity) {
        _movie.value = movie
    }
}