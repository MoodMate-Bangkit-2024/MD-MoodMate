package com.dicoding.moodmate.ui.journal.helper

import android.database.Cursor
import com.dicoding.moodmate.ui.journal.db.DatabaseContract
import com.dicoding.moodmate.ui.journal.entitiy.Journal

object MappingHelper {

    fun mapCursorToArrayList(journalsCursor: Cursor?): ArrayList<Journal> {
        val journalsList = ArrayList<Journal>()
        journalsCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.JournalColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.JournalColumns.TITLE))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.JournalColumns.DESCRIPTION))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.JournalColumns.DATE))
                journalsList.add(Journal(id, title, description, date))
            }
        }
        return journalsList
    }
}