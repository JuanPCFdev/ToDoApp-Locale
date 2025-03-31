package com.jpdev.datastoretodoapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.jpdev.datastoretodoapp.data.mapper.TaskMapper
import com.jpdev.datastoretodoapp.data.source.TaskLocalDataSource
import com.jpdev.datastoretodoapp.domain.model.Task
import com.jpdev.datastoretodoapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class TaskRepositoryImpl @Inject constructor(
    private val dataSource: TaskLocalDataSource,
    private val mapper: TaskMapper
) : TaskRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getTasks(): Flow<List<Task>> =
        dataSource.getTasks().map { tasks -> tasks.map(mapper::toDomain) }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addTask(task: Task) = dataSource.addTask(mapper.toData(task))

    override suspend fun deleteTask(taskId: String) = dataSource.deleteTask(taskId)

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateTask(task: Task) = dataSource.updateTask(mapper.toData(task))
}