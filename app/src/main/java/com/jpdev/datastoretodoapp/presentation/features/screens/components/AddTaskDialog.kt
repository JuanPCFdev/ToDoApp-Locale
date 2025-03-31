package com.jpdev.datastoretodoapp.presentation.features.screens.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.jpdev.datastoretodoapp.presentation.features.viewmodels.AddTaskViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    viewModel: AddTaskViewModel = hiltViewModel()
) {
    val title = viewModel.title.collectAsState()
    val description = viewModel.description.collectAsState()
    val dueDate = viewModel.dueDate.collectAsState()

    CustomAddTaskDialog(
        title = title.value,
        onTitleChange = { newTitle -> viewModel.onTitleChange(newTitle) },
        description = description.value,
        onDescriptionChange = { newDescription -> viewModel.onDescriptionChange(newDescription) },
        dueDate = dueDate.value,
        onDueDateChange = { newDueDate -> viewModel.onDueDateChange(newDueDate) },
        onConfirm = { title, description, dueDate -> viewModel.addTask() },
        onDismiss = { onDismiss() }
    )
}