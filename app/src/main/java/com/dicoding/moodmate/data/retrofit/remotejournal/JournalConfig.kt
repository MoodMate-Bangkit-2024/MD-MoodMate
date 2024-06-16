package com.dicoding.moodmate.data.retrofit.remotejournal

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JournalConfig {
    private const val BASE_URL = "http://34.101.171.205/"

    fun getJournalService(): JournalService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(JournalService::class.java)
    }
}