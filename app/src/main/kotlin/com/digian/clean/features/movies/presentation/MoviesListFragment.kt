package com.digian.clean.features.movies.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digian.clean.R
import com.digian.clean.core.data.exception.Failures
import com.digian.clean.databinding.FragmentMoviesBinding
import com.digian.clean.features.movies.domain.entities.MovieEntity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Alex Forrester on 24/04/2019.
 */
class MoviesListFragment : Fragment() {

    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var moviesListAdapter: MoviesListAdapter
    private lateinit var moviesViewManager: RecyclerView.LayoutManager

    private val moviesListViewModel: MoviesListViewModel by viewModel()

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        moviesViewManager = LinearLayoutManager(this.context)
        moviesListAdapter = MoviesListAdapter(object : OnItemClickListener {
            override fun onItemClick(movieEntity: MovieEntity) {

                val bundle = Bundle()
                bundle.putInt("movieId", movieEntity.id)
                view?.let { Navigation.findNavController(it).navigate(R.id.movieDetailFragment, bundle) };
            }

        })

        moviesRecyclerView = binding.moviesRecyclerView.apply {
            setHasFixedSize(true)

            layoutManager = moviesViewManager
            adapter = moviesListAdapter

            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }

        moviesListViewModel.movies.observe(viewLifecycleOwner,
            Observer<List<MovieEntity>> { popularMovies ->
                moviesListAdapter.data = popularMovies
                moviesListAdapter.notifyDataSetChanged()
            })

        moviesListViewModel.failure.observe(viewLifecycleOwner,
            Observer { failure ->
                Toast.makeText(
                    activity,
                    getString(R.string.movie_list_loading_error).plus((failure as? Failures)?.exception?.message),
                    Toast.LENGTH_LONG
                ).show()
            })

        moviesListViewModel.loadMovies()
    }
}
