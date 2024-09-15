package com.example.shayriapp

import androidx.room.TypeConverter
import java.util.Date


class DateConverter {

    // Convert a Long (timestamp) to a Date
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp != null) Date(timestamp) else null
    }

    // Convert a Date to a Long (timestamp)
    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}