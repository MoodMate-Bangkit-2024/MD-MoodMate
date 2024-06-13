package com.dicoding.moodmate.data.retrofit.remoteexplore

import com.dicoding.moodmate.data.response.ArticleResponse
import com.dicoding.moodmate.data.response.MovieResponse
import com.dicoding.moodmate.data.response.VideoResponse
import retrofit2.Response
import retrofit2.http.GET

interface ExploreService {

    @GET("article")
    suspend fun getArticle(): Response<ArticleResponse>

    @GET("video")
    suspend fun getVideo(): Response<VideoResponse>

    @GET("movie")
    suspend fun getMovie(): Response<MovieResponse>
}