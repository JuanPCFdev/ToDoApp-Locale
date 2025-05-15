package com.jpdev.datastoretodoapp.presentation.features.screens.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.jpdev.datastoretodoapp.presentation.features.viewmodels.AddTaskViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    viewModel: AddTaskViewModel = hiltViewModel()
) {
    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val dueDate by viewModel.dueDate.collectAsState()

    CustomAddTaskDialog(
        title = title,
        onTitleChange = { newTitle -> viewModel.onTitleChange(newTitle) },
        description = description,
        onDescriptionChange = { newDescription -> viewModel.onDescriptionChange(newDescription) },
        dueDate = dueDate,
        onDueDateChange = { newDueDate -> viewModel.onDueDateChange(newDueDate) },
        onConfirm = { title, description, dueDate -> viewModel.addTask() },
        onDismiss = { onDismiss() }
    )
}