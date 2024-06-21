package com.dicoding.moodmate.ui.recommendation.articleRec

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moodmate.data.util.Result
import com.dicoding.moodmate.databinding.ActivityRecArticleBinding
import com.dicoding.moodmate.ui.RecommendationModelFactory
import com.dicoding.moodmate.ui.ViewModelFactory
import com.dicoding.moodmate.ui.journal.JournalViewModel

class ArticleRecActivity : AppCompatActivity() {

    private val viewModel: JournalViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityRecArticleBinding
    private val articleRecViewModel: ArticleRecViewModel by viewModels {
        RecommendationModelFactory.getInstance()
    }
    private lateinit var articleRecAdapter: ArticleRecAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        val type = intent.getStringExtra("TYPE")?:""

        setupRecyclerView()
        observeViewModel()
        viewModel.user.observe(this){
            articleRecViewModel.getArticleRec(it.token, type)
        }
    }

    private fun setupRecyclerView() {
        articleRecAdapter = ArticleRecAdapter()
        binding.rvArticleRec.layoutManager = LinearLayoutManager(this)
        binding.rvArticleRec.adapter = articleRecAdapter
    }

    private fun observeViewModel() {
        articleRecViewModel.article.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    articleRecAdapter.submitList(result.data)
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