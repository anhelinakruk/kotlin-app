package com.example.dsw53518_kotlin

import android.app.Application
import androidx.room.Room
import com.example.dsw53518_kotlin.db.ReminderDatabase

class MainApplication: Application() {
    companion object {
        lateinit var reminderDatabase: ReminderDatabase
        lateinit var instance: MainApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        reminderDatabase = Room.databaseBuilder(
            applicationContext,
            ReminderDatabase::class.java,
            ReminderDatabase.NAME
        )
        .fallbackToDestructiveMigration()
        .build()
    }
}