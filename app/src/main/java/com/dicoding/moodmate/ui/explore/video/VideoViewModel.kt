package com.dicoding.moodmate.ui.explore.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.moodmate.data.repository.ExploreRepository
import com.dicoding.moodmate.data.response.VideoItem
import com.dicoding.moodmate.data.util.Result
import kotlinx.coroutines.launch

class VideoViewModel (private val repository: ExploreRepository) : ViewModel() {

    private val _video = MutableLiveData<Result<List<VideoItem>>>()
    val video: LiveData<Result<List<VideoItem>>> = _video

    fun getVideo() {
        viewModelScope.launch {
            _video.value = Result.Loading
            try {
                val video = repository.getVideo()
                _video.value = Result.Success(video)
            } catch (e: Exception) {
                _video.value = Result.Error(e)
            }
        }
    }
}