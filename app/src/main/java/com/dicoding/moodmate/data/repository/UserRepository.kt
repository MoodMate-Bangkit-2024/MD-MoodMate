package com.dicoding.moodmate.data.repository

import com.dicoding.moodmate.data.pref.UserModel
import com.dicoding.moodmate.data.pref.UserPreference
import com.dicoding.moodmate.data.response.LoginResponse
import com.dicoding.moodmate.data.response.RegisterResponse
import com.dicoding.moodmate.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun register(
        username: String,
        email: String,
        password: String
    ): RegisterResponse {
        return apiService.register(username, email, password)
    }

    suspend fun login(
        email: String,
        password: String
    ): LoginResponse {
        return apiService.login(email, password)
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }

        fun refresh() {
            instance = null
        }
    }
}
