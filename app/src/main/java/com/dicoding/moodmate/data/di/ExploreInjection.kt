package com.dicoding.moodmate.data.di

import com.dicoding.moodmate.data.repository.ExploreRepository
import com.dicoding.moodmate.data.retrofit.remoteexplore.ExploreConfig

object ExploreInjection {
    fun provideRepository(): ExploreRepository {
        val ExploreService = ExploreConfig.getExploreService()
        return ExploreRepository(ExploreService)
    }
}
