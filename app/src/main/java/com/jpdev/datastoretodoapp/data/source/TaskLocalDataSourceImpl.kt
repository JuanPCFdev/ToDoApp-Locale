package com.jpdev.datastoretodoapp.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jpdev.datastoretodoapp.data.model.TaskData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TaskLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val json: Json
) : TaskLocalDataSource {

    companion object {
        private val TASKS_KEY = stringPreferencesKey("tasks")
    }

    override fun getTasks(): Flow<List<TaskData>> {
        return dataStore.data.map { preferences ->
            preferences[TASKS_KEY]?.let {
                json.decodeFromString<List<TaskData>>(it)
            } ?: emptyList()
        }
    }

    override suspend fun addTask(task: TaskData) {
        dataStore.edit { preferences ->
            val currentTasks = preferences[TASKS_KEY]?.let {
                json.decodeFromString<List<TaskData>>(it)
            } ?: emptyList()
            preferences[TASKS_KEY] = json.encodeToString(currentTasks + task)
        }
    }

    override suspend fun deleteTask(taskId: String) {
        dataStore.edit { preferences ->
            val currentTasks = preferences[TASKS_KEY]?.let {
                json.decodeFromString<List<TaskData>>(it)
            } ?: emptyList()
            preferences[TASKS_KEY] = json.encodeToString(currentTasks.filter { it.id != taskId })
        }
    }

    override suspend fun updateTask(task: TaskData) {
        dataStore.edit { preferences ->
            val currentTasks = preferences[TASKS_KEY]?.let {
                json.decodeFromString<List<TaskData>>(it)
            } ?: emptyList()
            val updatedTasks = currentTasks.map { if (it.id == task.id) task else it }
            preferences[TASKS_KEY] = json.encodeToString(updatedTasks)
        }
    }

}