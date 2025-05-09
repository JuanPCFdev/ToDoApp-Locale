package com.jpdev.datastoretodoapp.presentation.features.screens.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun convertToEndOfDayLocal(year: Int, month: Int, day: Int): Long {
    return LocalDate.of(year, month + 1, day)
        .atTime(23, 59, 59)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateForDisplay(timestamp: Long): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun isOverdue(dueDateMillis: Long): Boolean {
    val now = ZonedDateTime.now(ZoneId.systemDefault())
    val dueDate = Instant.ofEpochMilli(dueDateMillis)
        .atZone(ZoneId.systemDefault())

    return now > dueDate
}