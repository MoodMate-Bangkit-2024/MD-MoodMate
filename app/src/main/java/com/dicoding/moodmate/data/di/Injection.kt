package com.dicoding.moodmate.data.di

import android.content.Context
import com.dicoding.moodmate.data.repository.UserRepository
import com.dicoding.moodmate.data.pref.UserPreference
import com.dicoding.moodmate.data.pref.dataStore
import com.dicoding.moodmate.data.retrofit.ApiConfig
import com.dicoding.moodmate.data.retrofit.chat.ApiChatConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        val apiChatService = ApiChatConfig.getApiChatService(user.token)
        return UserRepository.getInstance(apiService, apiChatService, pref)
    }

    fun refreshRepository() {
        UserRepository.refresh()
    }
}
