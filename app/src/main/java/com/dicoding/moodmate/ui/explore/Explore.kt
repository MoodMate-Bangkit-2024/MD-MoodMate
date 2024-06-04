package com.dicoding.moodmate.ui.explore

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Explore (
    val name: String,
    val photo: Int
) : Parcelable