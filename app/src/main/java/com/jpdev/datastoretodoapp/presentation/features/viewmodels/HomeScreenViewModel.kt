package com.jpdev.datastoretodoapp.presentation.features.viewmodels

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpdev.datastoretodoapp.domain.model.Status
import com.jpdev.datastoretodoapp.domain.model.Task
import com.jpdev.datastoretodoapp.domain.usecases.DeleteTaskUseCase
import com.jpdev.datastoretodoapp.domain.usecases.GetTasksUseCase
import com.jpdev.datastoretodoapp.domain.usecases.UpdateTaskStatusUseCase
import com.jpdev.datastoretodoapp.widget.TaskWidget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val updateTaskStatusUseCase: UpdateTaskStatusUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private val _tasksList = MutableStateFlow<List<Task>?>(null)
    val tasksList: StateFlow<List<Task>?> get() = _tasksList

    private val _uiState = MutableStateFlow<UiState>(UiState.Success)
    val uiState: StateFlow<UiState> get() = _uiState

    private val _showAddDialog = MutableStateFlow<Boolean>(false)
    val showAddDialog: StateFlow<Boolean> get() = _showAddDialog

    private val _pending = MutableStateFlow<Int>(0)
    val pending: StateFlow<Int> get() = _pending

    private val _inProgress = MutableStateFlow<Int>(0)
    val inProgress: StateFlow<Int> get() = _inProgress

    private val _done = MutableStateFlow<Int>(0)
    val done: StateFlow<Int> get() = _done

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                getTasksUseCase.invoke()
                    .collect { tasks ->
                        _tasksList.value = tasks
                        updateList()
                        _uiState.value = UiState.Success
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error al cargar datos")
            }
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            deleteTaskUseCase(taskId)
            updateList()
        }
    }

    fun updateTaskStatus(taskId: String, newStatus: Status) {
        viewModelScope.launch {
            updateTaskStatusUseCase(taskId, newStatus)
            updateList()
        }
    }

    fun onShowDialogChange() {
        _showAddDialog.value = !_showAddDialog.value
    }

    private fun updateList(){
        val listTasks = _tasksList.value

        _pending.value = 0
        _inProgress.value = 0
        _done.value = 0

        listTasks?.forEach { task ->
            when(task.status){
                Status.PENDING -> _pending.value ++
                Status.IN_PROGRESS -> _inProgress.value ++
                Status.DONE -> _done.value ++
            }
        }

    }



    sealed class UiState {
        data object Loading : UiState()
        data object Success : UiState()
        data class Error(val message: String) : UiState()
    }

}