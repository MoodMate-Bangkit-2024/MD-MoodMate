package com.dicoding.moodmate.ui.recommendation.movieRec

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.moodmate.data.util.Result
import com.dicoding.moodmate.databinding.ActivityRecMovieBinding
import com.dicoding.moodmate.ui.RecommendationModelFactory
import com.dicoding.moodmate.ui.ViewModelFactory
import com.dicoding.moodmate.ui.journal.JournalViewModel

class MovieRecActivity : AppCompatActivity() {

    private val viewModel: JournalViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityRecMovieBinding
    private val movieRecViewModel: MovieRecViewModel by viewModels {
        RecommendationModelFactory.getInstance()
    }
    private lateinit var movieRecAdapter: MovieRecAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        val type = intent.getStringExtra("TYPE")?:""

        setupRecyclerView()
        observeViewModel()
        viewModel.user.observe(this){
            movieRecViewModel.getMovieRec(it.token, type)
        }
    }

    private fun setupRecyclerView() {
        movieRecAdapter = MovieRecAdapter()
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvRecMovie.layoutManager = layoutManager
        binding.rvRecMovie.adapter = movieRecAdapter
    }

    private fun observeViewModel() {
        movieRecViewModel.movieRec.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    movieRecAdapter.submitList(result.data)
                }
                is Result.Error -> {
                    showLoading(false)
                    val errorMessage = result.message ?: "Unknown error occurred"
                    Toast.makeText(
                        this,
                        "Error: $errorMessage",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {}
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
