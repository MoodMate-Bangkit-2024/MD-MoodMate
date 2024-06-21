package com.dicoding.moodmate.ui.explore.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.moodmate.data.repository.ExploreRepository
import com.dicoding.moodmate.data.response.DataItem
import com.dicoding.moodmate.data.util.Result
import kotlinx.coroutines.launch

class ArticleViewModel(private val repository: ExploreRepository) : ViewModel() {

    private val _article = MutableLiveData<Result<List<DataItem>>>()
    val article: LiveData<Result<List<DataItem>>> = _article

    fun getArticle() {
        viewModelScope.launch {
            _article.value = Result.Loading
            try {
                val articles = repository.getArticle()
                _article.value = Result.Success(articles)
            } catch (e: Exception) {
                _article.value = Result.Error(e.message ?: "An error occurred")
            }
        }
    }
}
