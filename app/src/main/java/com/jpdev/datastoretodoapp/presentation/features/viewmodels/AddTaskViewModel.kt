package com.jpdev.datastoretodoapp.presentation.features.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpdev.datastoretodoapp.domain.model.Status
import com.jpdev.datastoretodoapp.domain.model.Task
import com.jpdev.datastoretodoapp.domain.usecases.AddTaskUseCase
import com.jpdev.datastoretodoapp.domain.usecases.ScheduleTaskExpiryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val scheduleUseCase: ScheduleTaskExpiryUseCase
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
                scheduleUseCase(newTask)

                _title.value = ""
                _description.value = ""
                _dueDate.value = System.currentTimeMillis()

            }
        }
    }

    private fun checkEmptySlots(): Boolean {
        return !(_title.value.isEmpty() || _description.value.isEmpty())
    }

}