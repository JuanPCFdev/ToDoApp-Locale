package com.jpdev.datastoretodoapp.domain.repository

import com.jpdev.datastoretodoapp.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun deleteTask(taskId: String)
    suspend fun updateTask(task: Task)
}