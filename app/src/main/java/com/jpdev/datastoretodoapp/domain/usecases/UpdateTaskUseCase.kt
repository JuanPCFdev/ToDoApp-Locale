package com.jpdev.datastoretodoapp.domain.usecases

import com.jpdev.datastoretodoapp.domain.model.Task
import com.jpdev.datastoretodoapp.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task) = repository.updateTask(task)
}