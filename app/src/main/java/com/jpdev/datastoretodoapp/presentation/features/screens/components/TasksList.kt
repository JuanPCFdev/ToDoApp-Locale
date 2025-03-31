package com.jpdev.datastoretodoapp.presentation.features.screens.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jpdev.datastoretodoapp.R
import com.jpdev.datastoretodoapp.domain.model.Status
import com.jpdev.datastoretodoapp.domain.model.Task
import com.jpdev.datastoretodoapp.presentation.theme.Red
import com.jpdev.datastoretodoapp.presentation.theme.White
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskList(
    tasks: List<Task>?,
    onDelete: (String) -> Unit,
    onStatusChange: (String, Status) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (tasks != null) {
            items(tasks) { tasks ->

                val swipeLeft = SwipeAction(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = stringResource(R.string.delete_task_icon),
                            modifier = Modifier
                                .size(48.dp)
                                .padding(12.dp),
                            tint = White
                        )
                    },
                    background = Red,
                    isUndo = true,
                    onSwipe = {
                        onDelete(tasks.id)
                    }
                )

                SwipeableActionsBox(endActions = listOf(swipeLeft)) {
                    TaskItem(
                        task = tasks,
                        onStatusChange = onStatusChange
                    )
                }

            }
        }else{
            item {
                Text(stringResource(R.string.no_tasks_created_yet))
            }
        }
    }
}