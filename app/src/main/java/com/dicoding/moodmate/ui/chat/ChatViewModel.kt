package com.dicoding.moodmate.ui.chat

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.moodmate.data.pref.UserModel
import com.dicoding.moodmate.data.repository.UserRepository
import com.dicoding.moodmate.data.response.ChatItem
import com.dicoding.moodmate.data.response.PromptResponse
import com.dicoding.moodmate.data.util.Result
import kotlinx.coroutines.launch

class ChatViewModel(private val repository: UserRepository) : ViewModel() {

    private val _chatRoom = MutableLiveData<List<ChatItem?>?>()
    val chatRoom: LiveData<List<ChatItem?>?> = _chatRoom

    val currentUser: LiveData<UserModel> = repository.getSession().asLiveData()

    private val _sendMessageResult = MutableLiveData<Result<PromptResponse>>()
    val sendMessageResult: LiveData<Result<PromptResponse>> = _sendMessageResult

    fun getChatRoom(author: String) {
        viewModelScope.launch {
            val result = repository.getChatRoom(author)
            if (result.isSuccess) {
                _chatRoom.value = result.getOrNull()?.data
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                Log.e("ChatViewModel", "Error fetching chat room: $errorMessage")
            }
        }
    }

    fun sendMessage(prompt: String) {
        viewModelScope.launch {
            _sendMessageResult.value = Result.Loading
            try {
                val response = repository.sendMessage(prompt)
                if (response.isSuccess) {
                    _sendMessageResult.value = Result.Success(response.getOrThrow())
                } else {
                    _sendMessageResult.value = Result.Error(response.exceptionOrNull()?.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _sendMessageResult.value = Result.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun reloadChatRoom(author: String) {
        viewModelScope.launch {
            val result = repository.getChatRoom(author)
            if (result.isSuccess) {
                _chatRoom.postValue(result.getOrNull()?.data)
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
                Log.e("ChatViewModel", "Error reloading chat room: $errorMessage")
            }
        }
    }
}
