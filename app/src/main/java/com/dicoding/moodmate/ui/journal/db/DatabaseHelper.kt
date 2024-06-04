package com.dicoding.moodmate.ui.journal.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dicoding.moodmate.ui.journal.db.DatabaseContract.JournalColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "dbjournalapp"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_JOURNAL = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.JournalColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.JournalColumns.TITLE} TEXT NOT NULL," +
                " ${DatabaseContract.JournalColumns.DESCRIPTION} TEXT NOT NULL," +
                " ${DatabaseContract.JournalColumns.DATE} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_JOURNAL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}