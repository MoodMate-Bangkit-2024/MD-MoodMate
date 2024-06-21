package com.dicoding.moodmate.ui.recommendation.articleRec

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.moodmate.data.repository.RecommendationRepository
import com.dicoding.moodmate.data.response.ArticleRecItem
import com.dicoding.moodmate.data.util.Result
import kotlinx.coroutines.launch

class ArticleRecViewModel (private val repository: RecommendationRepository) : ViewModel() {

    private val _article = MutableLiveData<Result<List<ArticleRecItem>>>()
    val article: LiveData<Result<List<ArticleRecItem>>> = _article

    fun getArticleRec(auth : String, type : String) {
        viewModelScope.launch {
            _article.value = Result.Loading
            try {
                val articles = repository.getArticleRec(type, auth)
                _article.value = Result.Success(articles)
            } catch (e: Exception) {
                _article.value = Result.Error(e.message ?: "An error occurred")
            }
        }
    }
}
