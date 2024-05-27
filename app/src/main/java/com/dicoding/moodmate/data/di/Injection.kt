package com.dicoding.moodmate.data.di

import android.content.Context
import com.dicoding.moodmate.data.UserRepository
import com.dicoding.moodmate.data.pref.UserPreference
import com.dicoding.moodmate.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}