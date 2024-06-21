package com.dicoding.moodmate.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.moodmate.data.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AccountViewModel(private val repository: UserRepository) : ViewModel() {
    fun getSession() = runBlocking { repository.getSession().first() }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
