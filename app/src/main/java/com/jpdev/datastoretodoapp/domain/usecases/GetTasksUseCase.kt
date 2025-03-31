package com.jpdev.datastoretodoapp.domain.usecases

import com.jpdev.datastoretodoapp.domain.model.Task
import com.jpdev.datastoretodoapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> {
        return repository.getTasks()
    }
}