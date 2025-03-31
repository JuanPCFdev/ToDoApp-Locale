package com.jpdev.datastoretodoapp.domain.usecases

import com.jpdev.datastoretodoapp.domain.model.Task
import com.jpdev.datastoretodoapp.domain.repository.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task) = repository.addTask(task)
}