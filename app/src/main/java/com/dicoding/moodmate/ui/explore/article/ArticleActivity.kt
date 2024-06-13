package com.dicoding.moodmate.ui.explore.article

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moodmate.databinding.ActivityArticleBinding
import com.dicoding.moodmate.data.util.Result
import com.dicoding.moodmate.ui.ExploreModelFactory

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding
    private val articleViewModel: ArticleViewModel by viewModels {
        ExploreModelFactory.getInstance()
    }
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
        articleViewModel.getArticle()
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter()
        binding.rvArticle.layoutManager = LinearLayoutManager(this)
        binding.rvArticle.adapter = articleAdapter
    }

    private fun observeViewModel() {
        articleViewModel.article.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    articleAdapter.submitList(result.data)
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
