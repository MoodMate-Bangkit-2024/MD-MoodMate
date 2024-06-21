package com.dicoding.moodmate.data.di

import com.dicoding.moodmate.data.repository.RecommendationRepository
import com.dicoding.moodmate.data.retrofit.recommendation.RecommendationConfig

object RecommendationInjection {
    fun provideRepository(): RecommendationRepository {
        val RecommendationService = RecommendationConfig.getRecommendationService()
        return RecommendationRepository(RecommendationService)
    }
}