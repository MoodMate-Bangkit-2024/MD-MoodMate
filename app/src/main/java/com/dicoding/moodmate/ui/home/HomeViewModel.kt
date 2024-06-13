package com.dicoding.moodmate.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.moodmate.ui.journal.db.JournalHelper
import com.dicoding.moodmate.ui.journal.entitiy.Journal
import com.dicoding.moodmate.ui.journal.helper.MappingHelper
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _journals = MutableLiveData<List<Journal>>()
    val journals: LiveData<List<Journal>> = _journals

    fun loadJournals(context: Context) {
        viewModelScope.launch {
            val journalHelper = JournalHelper.getInstance(context)
            try {
                journalHelper.open()
                val cursor = journalHelper.queryAll()
                val journals = MappingHelper.mapCursorToArrayList(cursor)
                _journals.postValue(journals)
            } catch (e: Exception) {
                // Handle exception
            } finally {
                journalHelper.close()
            }
        }
    }
}

