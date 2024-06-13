package com.dicoding.moodmate.ui.explore.movie

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.moodmate.data.util.Result
import com.dicoding.moodmate.databinding.ActivityMovieBinding
import com.dicoding.moodmate.ui.ExploreModelFactory

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding
    private val movieViewModel: MovieViewModel by viewModels {
        ExploreModelFactory.getInstance()
    }
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
        movieViewModel.getMovie()
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter()
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvMovie.layoutManager = layoutManager
        binding.rvMovie.adapter = movieAdapter
    }
    private fun observeViewModel() {
        movieViewModel.movie.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    movieAdapter.submitList(result.data)
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(
                        this,
                        "Error: ${result.exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}