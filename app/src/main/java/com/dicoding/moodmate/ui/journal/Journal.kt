package com.dicoding.moodmate.ui.journal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Journal(
    var id: String? = null,
    var title: String? = null,
    var text: String? = null,
    var mood: String? = null,
    var author: String? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null
): Parcelable
