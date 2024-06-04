package com.dicoding.moodmate.ui.chat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatMessage(
    val message: String,
    val isSentByUser: Boolean
) : Parcelable