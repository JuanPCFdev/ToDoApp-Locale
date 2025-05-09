package com.jpdev.datastoretodoapp.domain.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.jpdev.datastoretodoapp.data.scheduler.TaskScheduler
import com.jpdev.datastoretodoapp.domain.model.Task
import java.time.Instant
import javax.inject.Inject

class ScheduleTaskExpiryUseCase @Inject constructor(
    private val scheduler: TaskScheduler
) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(task: Task){
        scheduler.scheduleTaskExpiry(task.id, task.title, Instant.ofEpochMilli(task.dueDate))
    }
}