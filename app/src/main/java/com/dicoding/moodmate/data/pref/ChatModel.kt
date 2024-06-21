package com.dicoding.moodmate.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatModel (
    val type: String,
    val message: String
) : Parcelable

