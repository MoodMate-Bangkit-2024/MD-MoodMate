package com.dicoding.moodmate.ui.signup

import androidx.lifecycle.ViewModel
import com.dicoding.moodmate.data.UserRepository
import com.dicoding.moodmate.data.response.RegisterResponse

class SignupViewModel(private val repository: UserRepository): ViewModel() {
    suspend fun register(
        username: String,
        email: String,
        password: String
    ) : RegisterResponse {
        return repository.register(username, email, password)
    }
}