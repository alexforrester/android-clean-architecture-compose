package com.digian.clean.features.movies.presentation


import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.digian.clean.R
import com.digian.clean.core.data.exception.Failures
import com.digian.clean.databinding.FragmentMovieDetailBinding
import com.digian.clean.features.movies.domain.entities.GenreEntity
import com.digian.clean.features.movies.domain.entities.MovieEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

const val UNKNOWN_MOVIE_ID = 0
const val IMAGE_URL_AND_PATH = "https://image.tmdb.org/t/p/w400"
const val PICASSO_RESULT = "PICASSO_RESULT"

/**
 * Created by Alex Forrester on 23/04/2019.
 *
 * Fragment for displaying movie detail
 */
class MovieDetailFragment : Fragment() {


    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!


    private val movieDetailViewModel: MovieDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val movieId: Int = arguments?.getInt("movieId") ?: UNKNOWN_MOVIE_ID

        //Loads movie detail and returns from observer or displays error view
        movieDetailViewModel.movie.observe(viewLifecycleOwner,
            Observer<MovieEntity> { movie ->

                movie?.let { movieDetail ->
                    movieDetail.genres.let { genres ->

                        if (genres.isNotEmpty()) {
                            binding.movieGenres.visibility = View.VISIBLE
                            binding.movieGenres.text =
                                createGenreText(
                                    genres
                                )
                        }
                    }
                    binding.movieTitle.text = movieDetail.title
                    binding.movieDescription.text = movieDetail.overview
                    binding.movieVotes.text =
                        if (movieDetail.voteCount != -1) "VOTES: ${movieDetail.voteCount}" else ""
                    loadImageView(movieDetail.imagePath)
                    return@Observer
                }

            })

        movieDetailViewModel.failure.observe(viewLifecycleOwner,
            Observer { failure ->
                addErrorView(failure as? Failures)
            })

        movieDetailViewModel.loadMovie(movieId)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addErrorView(failureException: Failures?) {

        val errorTextView = TextView(activity)
        errorTextView.text = getString(R.string.movie_detail_loading_error).plus(
            failureException?.exception?.message ?: ""
        )
        errorTextView.gravity = Gravity.CENTER
        errorTextView.textSize = 20f
        errorTextView.layoutParams =
            ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            );
        binding.movieDetailRoot.addView(errorTextView)
    }

    private fun loadImageView(posterPath: String?) {

        val uri: Uri = Uri.parse(IMAGE_URL_AND_PATH.plus(posterPath))

        val picasso = Picasso.get()
        picasso.isLoggingEnabled = true

        picasso
            .load(uri)
            .error(R.drawable.ic_error_black_80dp)
            .noFade()
            .placeholder(R.drawable.placeholder460_690)
            .into(binding.movieImage, object : Callback {
                override fun onSuccess() {
                    Timber.d("%s onSuccess", PICASSO_RESULT)
                }

                override fun onError(e: Exception?) {
                    Timber.d("%s onFailure", PICASSO_RESULT)
                }
            })

    }

    companion object {
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
    }

}