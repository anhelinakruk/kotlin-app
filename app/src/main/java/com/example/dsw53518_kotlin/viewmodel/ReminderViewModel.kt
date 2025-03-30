package com.example.dsw53518_kotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dsw53518_kotlin.MainApplication
import com.example.dsw53518_kotlin.model.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class ReminderViewModel : ViewModel() {
    private val reminderDao = MainApplication.reminderDatabase.getReminderDao()

    val reminderList: LiveData<List<Reminder>> = reminderDao.getAllReminders()

    fun addReminder(title: String, reminderTime: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            reminderDao.addReminder(
                Reminder(
                    title = title,
                    createdAt = Date.from(Instant.now()),
                    reminderTime = Date(reminderTime)
                )
            )
        }
    }

    fun deleteReminder(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            reminderDao.deleteReminder(id)
        }
    }
}
