package com.jpdev.datastoretodoapp.presentation.features.screens.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jpdev.datastoretodoapp.R
import com.jpdev.datastoretodoapp.presentation.theme.BackgroundApp
import com.jpdev.datastoretodoapp.presentation.theme.Blue
import com.jpdev.datastoretodoapp.presentation.theme.CancelButton
import com.jpdev.datastoretodoapp.presentation.theme.White
import com.jpdev.datastoretodoapp.presentation.features.screens.utils.formatDateForDisplay
import showDatePickerWrapper

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAddTaskDialog(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    dueDate: Long,
    onDueDateChange: (Long) -> Unit,
    onConfirm: (String, String, Long) -> Unit,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current
    val outlinedColors = OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = Blue,
        focusedBorderColor = Blue,
        focusedTextColor = Blue,
        unfocusedTextColor = Blue
    )
    AlertDialog(
        containerColor = BackgroundApp,
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.title_new_task), color = White) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    colors = outlinedColors,
                    value = title,
                    onValueChange = onTitleChange,
                    label = { Text(stringResource(R.string.title), color = White) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    colors = outlinedColors,
                    value = description,
                    onValueChange = onDescriptionChange,
                    label = { Text(stringResource(R.string.description), color = White) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = formatDateForDisplay(dueDate),
                    onValueChange = {},
                    label = { Text(stringResource(R.string.due_date), color = Blue) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showDatePickerWrapper(context) {
                                onDueDateChange(it)
                            }
                        },
                    enabled = false,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = stringResource(R.string.select_date),
                            modifier = Modifier.clickable {
                                showDatePickerWrapper(context) {
                                    onDueDateChange(it)
                                }
                            },
                            tint = White
                        )
                    },
                    colors = outlinedColors
                )

            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotEmpty()) {
                        onConfirm(title, description, dueDate)
                        onDismiss()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                )
            ) {
                Text(stringResource(R.string.add), color = White)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = CancelButton)
            ) {
                Text(stringResource(R.string.cancel), color = White)
            }
        }
    )
}