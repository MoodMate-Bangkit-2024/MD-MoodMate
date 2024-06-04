package com.dicoding.moodmate.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {

    private val _chatMessages = MutableLiveData<List<ChatMessage>>()
    val chatMessages: LiveData<List<ChatMessage>> = _chatMessages

    init {
        _chatMessages.value = listOf(ChatMessage("Welcome to the chat!", false))
    }

    fun sendMessage(message: String) {
        val currentList = _chatMessages.value ?: listOf()
        val updatedList = currentList + ChatMessage(message, true)
        _chatMessages.value = updatedList
    }

        fun receiveMessage(message: String) {
        val currentList = _chatMessages.value ?: listOf()
        val updatedList = currentList + ChatMessage(message, false)
        _chatMessages.value = updatedList
    }
}
