package com.dicoding.moodmate.ui.recommendation.music

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.moodmate.data.repository.RecommendationRepository
import com.dicoding.moodmate.data.response.MusicItem
import com.dicoding.moodmate.data.util.Result
import kotlinx.coroutines.launch

class MusicViewModel (private val repository: RecommendationRepository) : ViewModel() {

    private val _music = MutableLiveData<Result<List<MusicItem>>>()
    val music: LiveData<Result<List<MusicItem>>> = _music

    fun getMusic(auth : String, type : String) {
        viewModelScope.launch {
            _music.value = Result.Loading
            try {
                val musics = repository.getMusic(type, auth)
                _music.value = Result.Success(musics)
            } catch (e: Exception) {
                _music.value = Result.Error(e.message ?: "An error occurred")
            }
        }
    }
}