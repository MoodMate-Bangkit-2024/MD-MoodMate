package com.dicoding.moodmate.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.moodmate.data.response.JournalData
import com.dicoding.moodmate.data.response.JournalResponse
import com.dicoding.moodmate.data.retrofit.remotejournal.JournalConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _journals = MutableLiveData<List<JournalData>>()
    val journals: LiveData<List<JournalData>> = _journals

    fun loadJournals(token: String) {
        viewModelScope.launch {
            val journalService = JournalConfig.getJournalService()

            journalService.getAllJournals(token).enqueue(object : Callback<JournalResponse> {
                override fun onResponse(
                    call: Call<JournalResponse>,
                    response: Response<JournalResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Journal Get", "Journals loaded: ${response.body()}")

                        val journalList: List<JournalData> = response.body()?.data ?: emptyList()
                        _journals.postValue(journalList)
                    } else {
                        Log.d("Journal Get", "Failed to load journals: ${response.code()}")
                        // Handle error
                    }
                }

                override fun onFailure(call: Call<JournalResponse>, t: Throwable) {
                    // Handle failure
                }
            })
        }
    }
}
