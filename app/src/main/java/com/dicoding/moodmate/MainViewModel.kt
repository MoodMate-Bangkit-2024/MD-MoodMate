package com.dicoding.moodmate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.moodmate.data.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession() = runBlocking { repository.getSession().first() }
}
