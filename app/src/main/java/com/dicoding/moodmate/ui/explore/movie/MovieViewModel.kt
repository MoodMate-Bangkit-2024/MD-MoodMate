package com.dicoding.moodmate.ui.explore.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.moodmate.data.repository.ExploreRepository
import com.dicoding.moodmate.data.response.MovieItem
import com.dicoding.moodmate.data.util.Result
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: ExploreRepository) : ViewModel() {

    private val _movie = MutableLiveData<Result<List<MovieItem>>>()
    val movie: LiveData<Result<List<MovieItem>>> = _movie

    fun getMovie() {
        viewModelScope.launch {
            _movie.value = Result.Loading
            try {
                val movie = repository.getMovie()
                _movie.value = Result.Success(movie)
            } catch (e: Exception) {
                _movie.value = Result.Error(e.message ?: "An error occurred")
            }
        }
    }
}
