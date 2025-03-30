package com.example.dsw53518_kotlin

import android.app.Application
import androidx.room.Room
import com.example.dsw53518_kotlin.db.ReminderDatabase

class MainApplication: Application() {
    companion object {
        lateinit var reminderDatabase: ReminderDatabase
    }

    override fun onCreate() {
        super.onCreate()
        reminderDatabase = Room.databaseBuilder(
            applicationContext,
            ReminderDatabase::class.java,
            ReminderDatabase.NAME
        ).build()
    }
}