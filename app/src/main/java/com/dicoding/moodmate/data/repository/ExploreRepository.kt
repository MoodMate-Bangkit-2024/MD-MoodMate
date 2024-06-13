package com.dicoding.moodmate.data.repository

import com.dicoding.moodmate.data.response.DataItem
import com.dicoding.moodmate.data.response.MovieItem
import com.dicoding.moodmate.data.response.VideoItem
import com.dicoding.moodmate.data.retrofit.remoteexplore.ExploreService

class ExploreRepository(private val apiService: ExploreService) {

    suspend fun getArticle(): List<DataItem> {
        val response = apiService.getArticle()
        if (response.isSuccessful) {
            return response.body()?.data?.filterNotNull() ?: emptyList()
        } else {
            throw Exception("Failed to load articles: ${response.message()}")
        }
    }

    suspend fun getVideo(): List<VideoItem> {
        val response = apiService.getVideo()
        if (response.isSuccessful) {
            return response.body()?.data?.filterNotNull() ?: emptyList()
        } else {
            throw Exception("Failed to load video: ${response.message()}")
        }
    }

    suspend fun getMovie(): List<MovieItem> {
        val response = apiService.getMovie()
        if (response.isSuccessful) {
            return response.body()?.data?.filterNotNull() ?: emptyList()
        } else {
            throw Exception("Failed to load movie: ${response.message()}")
        }
    }
}