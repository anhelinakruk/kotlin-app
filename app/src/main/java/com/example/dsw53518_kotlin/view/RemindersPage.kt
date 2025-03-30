package com.example.dsw53518_kotlin.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dsw53518_kotlin.R
import com.example.dsw53518_kotlin.ui.theme.DarkPurple
import com.example.dsw53518_kotlin.ui.theme.Pink
import com.example.dsw53518_kotlin.viewmodel.ReminderViewModel
import com.example.dsw53518_kotlin.model.Reminder
import com.example.dsw53518_kotlin.utils.Routes
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun RemindersPage(viewModel: ReminderViewModel, navController: NavController) {
    val reminderList by viewModel.reminderList.observeAsState()
    val context = LocalContext.current

    var inputText by remember { mutableStateOf("") }
    var reminderDateTime by remember { mutableStateOf<Long?>(null) }

    val calendar = remember { Calendar.getInstance() }

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            reminderDateTime = calendar.timeInMillis
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            timePickerDialog.show()
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    fun formatReminderDateTime(timestamp: Long?): String {
        return if (timestamp == null) {
            "Select date & time"
        } else {
            SimpleDateFormat("hh:mm aa, dd/MM/yyyy", Locale.ENGLISH).format(timestamp)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 40.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigate(Routes.homePage) }) {
                Icon(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = "Back",
                    tint = DarkPurple,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Reminders",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = DarkPurple,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Add new reminder") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Pink,
                cursorColor = Pink,
                focusedLabelColor = Pink
            ),
            textStyle = TextStyle(fontSize = 18.sp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { datePickerDialog.show() },
                colors = ButtonDefaults.buttonColors(containerColor = Pink),
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp)
            ) {
                Text(
                    text = formatReminderDateTime(reminderDateTime),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = {
                    if (inputText.isNotBlank() && reminderDateTime != null) {
                        viewModel.addReminder(inputText.trim(), reminderDateTime!!)
                        inputText = ""
                        reminderDateTime = null
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = DarkPurple),
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp)
            ) {
                Text(
                    text = "Add",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (reminderList.isNullOrEmpty()) {
            Text(
                text = "No reminders yet",
                fontSize = 16.sp,
                color = DarkPurple.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(reminderList!!) { _, item ->
                    ReminderItem(item = item, onDelete = { viewModel.deleteReminder(item.id) })
                }
            }
        }
    }
}

@Composable
fun ReminderItem(item: Reminder, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Pink.copy(alpha = 0.15f))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.reminder_icon),
            contentDescription = "Reminder Icon",
            tint = DarkPurple,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = SimpleDateFormat("hh:mm aa, dd/MM/yyyy", Locale.ENGLISH).format(item.reminderTime ?: item.createdAt),
                fontSize = 12.sp,
                color = DarkPurple.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkPurple
            )
        }
        IconButton(onClick = onDelete) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = "Delete reminder",
                tint = Pink
            )
        }
    }
}
