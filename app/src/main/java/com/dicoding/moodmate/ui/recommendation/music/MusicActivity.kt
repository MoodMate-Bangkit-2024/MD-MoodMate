package com.dicoding.moodmate.ui.recommendation.music

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moodmate.data.util.Result
import com.dicoding.moodmate.databinding.ActivityMusicBinding
import com.dicoding.moodmate.ui.RecommendationModelFactory
import com.dicoding.moodmate.ui.ViewModelFactory
import com.dicoding.moodmate.ui.journal.JournalViewModel

class MusicActivity : AppCompatActivity() {

    private val viewModel: JournalViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMusicBinding
    private val musicViewModel: MusicViewModel by viewModels {
        RecommendationModelFactory.getInstance()
    }
    private lateinit var musicAdapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        val type = intent.getStringExtra("TYPE")?:""

        setupRecyclerView()
        observeViewModel()
        viewModel.user.observe(this) { user ->

            musicViewModel.getMusic(user.token, type)

        }
    }

    private fun setupRecyclerView() {
        musicAdapter = MusicAdapter()
        binding.rvMusic.layoutManager = LinearLayoutManager(this)
        binding.rvMusic.adapter = musicAdapter
    }

    private fun observeViewModel() {
        musicViewModel.music.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    musicAdapter.submitList(result.data)
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