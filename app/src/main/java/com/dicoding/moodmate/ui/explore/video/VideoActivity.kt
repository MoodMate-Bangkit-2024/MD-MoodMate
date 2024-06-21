package com.dicoding.moodmate.ui.explore.video

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.moodmate.data.util.Result
import com.dicoding.moodmate.databinding.ActivityVideoBinding
import com.dicoding.moodmate.ui.ExploreModelFactory

class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding
    private val videoViewModel: VideoViewModel by viewModels {
        ExploreModelFactory.getInstance()
    }
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
        videoViewModel.getVideo()
    }

    private fun setupRecyclerView() {
        videoAdapter = VideoAdapter()
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvVideo.layoutManager = layoutManager
        binding.rvVideo.adapter = videoAdapter
    }

    private fun observeViewModel() {
        videoViewModel.video.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    videoAdapter.submitList(result.data)
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
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
