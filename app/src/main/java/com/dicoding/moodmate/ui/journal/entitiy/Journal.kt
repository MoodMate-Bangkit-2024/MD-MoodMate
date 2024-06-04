package com.dicoding.moodmate.ui.journal.entitiy
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Journal(
    var id: Int = 0,
    var title: String? = null,
    var description: String? = null,
    var date: String? = null
): Parcelable
