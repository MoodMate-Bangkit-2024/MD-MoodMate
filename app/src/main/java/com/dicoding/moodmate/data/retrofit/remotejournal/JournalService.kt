package com.dicoding.moodmate.data.retrofit.remotejournal

import com.dicoding.moodmate.data.response.JournalResponse
import com.dicoding.moodmate.data.response.SingleJournalData
import com.dicoding.moodmate.data.response.SingleJournalResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JournalService {
    @GET("journals")
    fun getAllJournals(@Header("Authorization") auth: String): Call<JournalResponse>

    @GET("journals/{id}")
    fun getJournalById(
        @Path("id") id: String,
        @Header("Authorization") auth: String
    ): Call<SingleJournalResponse>

    @POST("predict/mood")
    fun createJournal(
        @Body journal: SingleJournalData,
        @Header("Authorization") auth: String
    ): Call<SingleJournalResponse>

    @PUT("journals/{id}")
    fun updateJournal(
        @Path("id") id: String,
        @Body journal: SingleJournalData,
        @Header("Authorization") auth: String
    ): Call<SingleJournalResponse>

    @DELETE("journals/{id}")
    fun deleteJournal(
        @Path("id") id: String,
        @Header("Authorization") auth: String
    ): Call<SingleJournalResponse>
}