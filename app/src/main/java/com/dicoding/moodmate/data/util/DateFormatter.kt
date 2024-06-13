package com.dicoding.moodmate.data.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateFormatter {
    fun formatDate(dateString: String, timeZone: String): String {
        val inputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID")) // Assuming the locale is Indonesian
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone(timeZone)
        val date: Date = inputFormat.parse(dateString) ?: return ""
        return outputFormat.format(date)
    }
}