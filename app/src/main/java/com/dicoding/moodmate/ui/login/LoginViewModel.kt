package com.dicoding.moodmate.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.moodmate.data.UserRepository
import com.dicoding.moodmate.data.pref.UserModel
import com.dicoding.moodmate.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    suspend fun login(
        email:String,
        password:String
    ): LoginResponse {
        return repository.login(email,password)
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}