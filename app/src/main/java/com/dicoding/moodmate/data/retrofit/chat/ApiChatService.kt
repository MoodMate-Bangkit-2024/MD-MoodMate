package com.dicoding.moodmate.data.retrofit.chat

import com.dicoding.moodmate.data.response.ChatResponse
import com.dicoding.moodmate.data.response.PromptResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiChatService {

    @GET("room/{author}")
    suspend fun getChatRoom(
        @Header("Authorization") authorization: String,
        @Path("author") author: String
    ): ChatResponse
    @FormUrlEncoded
    @POST("prompt")
    suspend fun sendMessage(
        @Field("prompt") prompt: String
    ): PromptResponse
}
