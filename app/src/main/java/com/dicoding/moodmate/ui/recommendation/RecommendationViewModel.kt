package com.dicoding.moodmate.ui.recommendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.moodmate.data.repository.RecommendationRepository
import com.dicoding.moodmate.data.response.ArticleRecItem
import com.dicoding.moodmate.data.response.MovieRecItem
import com.dicoding.moodmate.data.response.MusicItem
import kotlinx.coroutines.launch

class RecommendationViewModel (private val repository: RecommendationRepository) : ViewModel(){

    private val _articleList = MutableLiveData<List<ArticleRecItem>>()
    val articles: LiveData<List<ArticleRecItem>> get() = _articleList
    private val _movieList = MutableLiveData<List<MovieRecItem>>()
    val movies: LiveData<List<MovieRecItem>> get() = _movieList
    private val _musicList = MutableLiveData<List<MusicItem>>()
    val musics: LiveData<List<MusicItem>> get() = _musicList

    private fun loadMusicData(type : String, auth : String) {
        viewModelScope.launch {
            _musicList.value = repository.getMusic(type, auth)
        }
    }

    private fun loadMovieData(type : String, auth : String) {
        viewModelScope.launch {
            _movieList.value = repository.getMovieRec(type, auth)
        }
    }

    private fun loadArticleData(type : String, auth : String) {
        viewModelScope.launch {
            _articleList.value = repository.getArticleRec(type, auth)
        }
    }
}