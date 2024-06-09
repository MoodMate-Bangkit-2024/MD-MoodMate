package com.dicoding.moodmate.ui.welcome

import androidx.lifecycle.ViewModel
import com.dicoding.moodmate.data.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class WelcomeViewModel(private val repository: UserRepository) : ViewModel() {

    fun getSession() = runBlocking {
        repository.getSession().first()
    }
}
