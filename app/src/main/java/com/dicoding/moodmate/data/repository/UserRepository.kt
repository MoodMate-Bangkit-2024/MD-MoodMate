package com.dicoding.moodmate.data.repository

import android.util.Log
import com.dicoding.moodmate.data.pref.UserModel
import com.dicoding.moodmate.data.pref.UserPreference
import com.dicoding.moodmate.data.response.ChatResponse
import com.dicoding.moodmate.data.response.ErrorResponse
import com.dicoding.moodmate.data.response.LoginResponse
import com.dicoding.moodmate.data.response.PromptResponse
import com.dicoding.moodmate.data.response.RegisterResponse
import com.dicoding.moodmate.data.retrofit.ApiService
import com.dicoding.moodmate.data.retrofit.chat.ApiChatService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val apiChatService: ApiChatService,
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
    suspend fun getChatRoom(author: String): Result<ChatResponse> {
        return try {
            val user = userPreference.getSession().first()
            val authorization = "Bearer ${user.token}"
            val response = apiChatService.getChatRoom(authorization, author)
            if (!response.error!!) {
                Result.success(response)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: HttpException) {
            Log.e("ChatFragment", "API Error: ${e.message()}")
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            Result.failure(Exception(errorResponse.message ?: "Load Chat Room failed"))
        } catch (e: Exception) {
            Result.failure(Exception("Load Chat Room failed"))
        }
    }

    suspend fun sendMessage(prompt: String): Result<PromptResponse> {
        return try {
            val response = apiChatService.sendMessage(prompt)
            if (response.error == false) {
                Result.success(response)
            } else {
                Result.failure(Exception(response.message))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            Result.failure(Exception("Send Message failed: ${errorResponse.message}"))
        } catch (e: Exception) {
            Result.failure(Exception("Send Message failed: ${e.message}"))
        }
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
            apiChatService: ApiChatService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, apiChatService, userPreference)
            }.also { instance = it }

        fun refresh() {
            instance = null
        }
    }
}
