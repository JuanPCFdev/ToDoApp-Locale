package com.jpdev.datastoretodoapp.presentation.features.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.jpdev.datastoretodoapp.domain.model.Status
import com.jpdev.datastoretodoapp.domain.model.Task
import com.jpdev.datastoretodoapp.domain.usecases.AddTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jpdev.datastoretodoapp.R
import com.jpdev.datastoretodoapp.data.worker.DueDateWorker
import com.jpdev.datastoretodoapp.notifications.NotificationUtils
import java.util.concurrent.TimeUnit

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _title = MutableStateFlow<String>("")
    val title: StateFlow<String> get() = _title

    private val _description = MutableStateFlow<String>("")
    val description: StateFlow<String> get() = _description

    private val _dueDate = MutableStateFlow<Long>(System.currentTimeMillis())
    val dueDate: StateFlow<Long> get() = _dueDate

    fun onTitleChange(newTitle: String) {
        if (newTitle.length <= 20) {
            _title.value = newTitle
        }
    }

    fun onDescriptionChange(newDescription: String) {
        _description.value = newDescription
    }

    fun onDueDateChange(newDueDate: Long) {
        _dueDate.value = newDueDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addTask() {
        viewModelScope.launch {
            if (checkEmptySlots()) {
                val newTask = Task(
                    id = System.currentTimeMillis().toString(),
                    title = _title.value,
                    description = _description.value,
                    creationDate = System.currentTimeMillis(),
                    dueDate = _dueDate.value,
                    status = Status.PENDING
                )

                addTaskUseCase(newTask)

                sendInstantNotification(newTask)
                scheduleDueDateNotification(newTask)

                _title.value = ""
                _description.value = ""
                _dueDate.value = System.currentTimeMillis()

            }
        }
    }

    private fun checkEmptySlots(): Boolean {
        return !(_title.value.isEmpty() || _description.value.isEmpty())
    }

    @SuppressLint("MissingPermission")
    private fun sendInstantNotification(task: Task) {
        val notification = NotificationCompat.Builder(context, NotificationUtils.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Nueva tarea añadida")
            .setContentText("Has añadido: ${task.title}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), notification)
        }
    }

    private fun scheduleDueDateNotification(task: Task) {
        val delay = task.dueDate - System.currentTimeMillis()
        if (delay <= 0) return

        val data = workDataOf("title" to task.title)
        val request = OneTimeWorkRequestBuilder<DueDateWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(request)
    }

}