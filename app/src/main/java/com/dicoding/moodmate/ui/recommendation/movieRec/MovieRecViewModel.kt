package com.dicoding.moodmate.ui.recommendation.movieRec

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.moodmate.data.repository.RecommendationRepository
import com.dicoding.moodmate.data.response.MovieRecItem
import com.dicoding.moodmate.data.util.Result
import kotlinx.coroutines.launch

class MovieRecViewModel (private val repository: RecommendationRepository) : ViewModel() {

    private val _movieRec = MutableLiveData<Result<List<MovieRecItem>>>()
    val movieRec: LiveData<Result<List<MovieRecItem>>> = _movieRec

    fun getMovieRec(auth : String, type : String) {
        viewModelScope.launch {
            _movieRec.value = Result.Loading
            try {
                val movieRec = repository.getMovieRec(type, auth)
                _movieRec.value = Result.Success(movieRec)
            } catch (e: Exception) {
                _movieRec.value = Result.Error(e.message ?: "An error occurred")
            }
        }
    }
}
