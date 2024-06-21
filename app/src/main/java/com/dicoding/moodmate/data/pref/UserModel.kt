package com.dicoding.moodmate.data.pref

data class UserModel(
    val name: String,
    val email: String,
    val token: String,
    val userId: String,
    val isLogin: Boolean = false
)



