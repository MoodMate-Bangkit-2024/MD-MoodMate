package com.dicoding.moodmate.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)
