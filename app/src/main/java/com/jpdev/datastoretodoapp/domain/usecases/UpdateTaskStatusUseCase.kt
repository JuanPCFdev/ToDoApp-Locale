package com.jpdev.datastoretodoapp.domain.usecases

import com.jpdev.datastoretodoapp.domain.model.Status
import com.jpdev.datastoretodoapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateTaskStatusUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: String, newStatus: Status) {
        val currentTasks = repository.getTasks().first()
        val taskToUpdate = currentTasks.find { it.id == taskId }
        taskToUpdate?.let {
            repository.updateTask(it.copy(status = newStatus))
        }
    }
}