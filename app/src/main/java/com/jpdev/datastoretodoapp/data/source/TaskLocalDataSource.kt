package com.jpdev.datastoretodoapp.data.source

import com.jpdev.datastoretodoapp.data.model.TaskData
import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {
    fun getTasks(): Flow<List<TaskData>>
    suspend fun addTask(task: TaskData)
    suspend fun deleteTask(taskId: String)
    suspend fun updateTask(task: TaskData)
}