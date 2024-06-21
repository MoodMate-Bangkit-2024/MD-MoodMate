package com.dicoding.moodmate.data.retrofit.recommendation

import com.dicoding.moodmate.data.response.ArticleRecResponse
import com.dicoding.moodmate.data.response.MovieRecResponse
import com.dicoding.moodmate.data.response.MusicResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RecommendationService {
    @GET("recommendation/music/{type}")
    suspend fun getMusic(
        @Path("type") type: String,
        @Header("Authorization") auth: String
    ): Response<MusicResponse>

    @GET("recommendation/article/{type}")
    suspend fun getArticle(
        @Path("type") type: String,
        @Header("Authorization") auth: String
    ): Response<ArticleRecResponse>

    @GET("recommendation/movie/{type}")
    suspend fun getMovie(
        @Path("type") type: String,
        @Header("Authorization") auth: String
    ): Response<MovieRecResponse>
}