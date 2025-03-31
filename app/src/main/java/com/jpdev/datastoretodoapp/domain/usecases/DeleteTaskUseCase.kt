package com.jpdev.datastoretodoapp.domain.usecases

import com.jpdev.datastoretodoapp.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: String) = repository.deleteTask(taskId)
}