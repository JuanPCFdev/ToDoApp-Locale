package com.jpdev.datastoretodoapp.domain.usecases

import com.jpdev.datastoretodoapp.data.scheduler.TaskScheduler
import javax.inject.Inject

class CancelTaskExpiryUseCase @Inject constructor(
    private val scheduler: TaskScheduler
) {
    operator fun invoke(taskId: String){
        scheduler.cancelTaskExpiry(taskId)
    }
}