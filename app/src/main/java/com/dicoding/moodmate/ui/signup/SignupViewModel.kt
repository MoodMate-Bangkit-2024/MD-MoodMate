package com.dicoding.moodmate.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.moodmate.data.repository.UserRepository
import com.dicoding.moodmate.data.response.ErrorResponse
import com.dicoding.moodmate.data.response.RegisterResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignupViewModel(private val repository: UserRepository) : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private var _errorResponse = MutableLiveData<ErrorResponse>()
    val errorResponse: LiveData<ErrorResponse> = _errorResponse

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.register(name, email, password)
                _registerResponse.value = response
            } catch (e: HttpException) {
                //get error message
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                _errorResponse.value = ErrorResponse(true, errorMessage)
            } catch (e: Exception) {
                _errorResponse.value = ErrorResponse(true, e.message.toString())
            } finally {
                _isLoading.value = false
            }

        }
    }
}

