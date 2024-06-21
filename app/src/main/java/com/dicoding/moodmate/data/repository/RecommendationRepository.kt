package com.dicoding.moodmate.data.repository

import com.dicoding.moodmate.data.response.ArticleRecItem
import com.dicoding.moodmate.data.response.MovieRecItem
import com.dicoding.moodmate.data.response.MusicItem
import com.dicoding.moodmate.data.retrofit.recommendation.RecommendationService

class RecommendationRepository(private val apiService: RecommendationService) {
    suspend fun getArticleRec(type: String, auth: String): List<ArticleRecItem> {
        val response = apiService.getArticle(type, auth)
        if (response.isSuccessful) {
            return response.body()?.data?.filterNotNull() ?: emptyList()
        } else {
            throw Exception("Failed to load articles: ${response.message()}")
        }
    }

    suspend fun getMusic(type: String, auth: String): List<MusicItem> {
        val response = apiService.getMusic(type, auth)
        if (response.isSuccessful) {
            return response.body()?.data?.filterNotNull() ?: emptyList()
        } else {
            throw Exception("Failed to load music: ${response.message()}")
        }
    }

    suspend fun getMovieRec(type: String, auth: String): List<MovieRecItem> {
        val response = apiService.getMovie(type, auth)
        if (response.isSuccessful) {
            return response.body()?.data?.filterNotNull() ?: emptyList()
        } else {
            throw Exception("Failed to load movie: ${response.message()}")
        }
    }
}