package com.jpdev.datastoretodoapp.data.worker

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jpdev.datastoretodoapp.util.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TaskExpiryWorker @AssistedInject constructor (
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val helper: NotificationHelper
): CoroutineWorker(appContext, params) {
    @SuppressLint("RestrictedApi")
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        val taskId = inputData.getString("task_id") ?: return Result.failure()
        val taskName = inputData.getString("task_name") ?: "_"
        helper.showTaskExpiredNotification(taskId, taskName)
        return Result.Success()
    }
}