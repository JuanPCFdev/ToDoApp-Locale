package com.jpdev.datastoretodoapp.data.scheduler

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.jpdev.datastoretodoapp.data.worker.TaskExpiryWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import java.time.Instant
import java.util.concurrent.TimeUnit
import javax.inject.Inject


interface TaskScheduler {
    fun scheduleTaskExpiry(taskId: String, title: String, dueTime: Instant)
    fun cancelTaskExpiry(taskId: String)
}

class WorkManagerTaskScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
) : TaskScheduler {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun scheduleTaskExpiry(
        taskId: String,
        title: String,
        dueTime: Instant,
    ) {
        val delay = Duration.between(Instant.now(), dueTime).toMillis().coerceAtLeast(0)
        val data = workDataOf("task_id" to taskId, "task_name" to title)

        val request = OneTimeWorkRequestBuilder<TaskExpiryWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .addTag(taskId)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(taskId, ExistingWorkPolicy.REPLACE, request)
    }

    override fun cancelTaskExpiry(taskId: String) {
        WorkManager.getInstance(context)
            .cancelAllWorkByTag(taskId)
    }

}