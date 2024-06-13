package com.dicoding.moodmate.ui.journal.db

import android.provider.BaseColumns

class DatabaseContract {
    internal class JournalColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "journal"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DATE = "date"
        }
    }
}