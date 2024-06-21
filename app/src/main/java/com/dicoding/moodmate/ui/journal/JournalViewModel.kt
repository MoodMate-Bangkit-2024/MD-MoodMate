package com.dicoding.moodmate.ui.journal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.moodmate.data.pref.UserModel
import com.dicoding.moodmate.data.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class JournalViewModel (private val repository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = _user
    init {
        getSession()
    }

    fun getSession() = viewModelScope.launch {
        repository.getSession().collect{
            _user.value = it
        }
    }
}