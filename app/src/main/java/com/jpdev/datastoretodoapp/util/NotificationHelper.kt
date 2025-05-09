package com.jpdev.datastoretodoapp.util

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.jpdev.datastoretodoapp.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.jpdev.datastoretodoapp.R

class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showTaskExpiredNotification(taskId: String, title: String){
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("TASK_ID",taskId)
        }
        val pending = PendingIntent.getActivity(
            context, taskId.hashCode(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, "tasks_channel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Ha vencido una tarea")
            .setContentTitle("La tarea $title ha llegado a su fecha limite.")
            .setContentIntent(pending)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(taskId.hashCode(), notification)
    }
}