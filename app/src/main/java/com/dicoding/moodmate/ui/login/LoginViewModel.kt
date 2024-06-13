package com.dicoding.moodmate.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.moodmate.data.repository.UserRepository
import com.dicoding.moodmate.data.pref.UserModel
import com.dicoding.moodmate.data.response.LoginResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _loginResponseLiveData = MutableLiveData<LoginResponse?>()
    var loginResponseLiveData: MutableLiveData<LoginResponse?> = _loginResponseLiveData

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val response = repository.login(email, password)
                _loginResponseLiveData.postValue(response)
            } catch (e: Exception) {
                _loginResponseLiveData.postValue(null)
            } finally {
                _isLoading.postValue(false)
            }

        }
    }

    fun getSession() = runBlocking {
        repository.getSession().first()
    }
}