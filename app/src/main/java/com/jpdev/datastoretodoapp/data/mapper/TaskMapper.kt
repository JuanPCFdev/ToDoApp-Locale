package com.jpdev.datastoretodoapp.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.jpdev.datastoretodoapp.data.model.TaskData
import com.jpdev.datastoretodoapp.domain.model.Task

class TaskMapper {

    @RequiresApi(Build.VERSION_CODES.O)
    fun toDomain(taskData: TaskData): Task = Task(
        id = taskData.id,
        title = taskData.title,
        description = taskData.description,
        creationDate = taskData.creationDate,
        dueDate = taskData.dueDate,
        status = enumValueOf(taskData.status)
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun toData(task: Task): TaskData = TaskData(
        id = task.id,
        title = task.title,
        description = task.description,
        creationDate = task.creationDate,
        dueDate = task.dueDate,
        status = task.status.name
    )

}