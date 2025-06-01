package com.example.dsw53518_kotlin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dsw53518_kotlin.model.Reminder

@Database(entities = [Reminder::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ReminderDatabase : RoomDatabase() {
    companion object {
        const val NAME = "Reminder_DB"
    }

    abstract fun getReminderDao(): ReminderDao
}
