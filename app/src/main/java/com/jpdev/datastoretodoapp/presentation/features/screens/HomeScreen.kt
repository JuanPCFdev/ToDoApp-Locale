package com.jpdev.datastoretodoapp.presentation.features.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.glance.appwidget.updateAll
import androidx.hilt.navigation.compose.hiltViewModel
import com.jpdev.datastoretodoapp.R.string
import com.jpdev.datastoretodoapp.presentation.features.screens.components.AddTaskDialog
import com.jpdev.datastoretodoapp.presentation.features.screens.components.CurrentTasksCard
import com.jpdev.datastoretodoapp.presentation.features.screens.components.CustomAppBar
import com.jpdev.datastoretodoapp.presentation.features.screens.components.FabCustom
import com.jpdev.datastoretodoapp.presentation.features.screens.components.TaskList
import com.jpdev.datastoretodoapp.presentation.features.viewmodels.HomeScreenViewModel
import com.jpdev.datastoretodoapp.presentation.theme.BackgroundApp
import com.jpdev.datastoretodoapp.presentation.theme.Green
import com.jpdev.datastoretodoapp.presentation.theme.Purple
import com.jpdev.datastoretodoapp.presentation.theme.Red
import com.jpdev.datastoretodoapp.presentation.theme.White
import com.jpdev.datastoretodoapp.widget.TaskWidget

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val taskList by viewModel.tasksList.collectAsState()
    val showDialog by viewModel.showAddDialog.collectAsState()
    val pending by viewModel.pending.collectAsState()
    val inProgress by viewModel.inProgress.collectAsState()
    val done by viewModel.done.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(true) {
        TaskWidget().updateAll(context)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FabCustom(onClick = {
                viewModel.onShowDialogChange()
            })
        },
        topBar = {
            CustomAppBar()
        },
        containerColor = BackgroundApp
    ) { padding ->

        when (uiState) {
            is HomeScreenViewModel.UiState.Error -> {
                Text(
                    text = (uiState as HomeScreenViewModel.UiState.Error).message,
                    color = Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            HomeScreenViewModel.UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            HomeScreenViewModel.UiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    StatsCards(
                        pending = pending,
                        inProgress = inProgress,
                        done = done
                    )
                    TaskList(
                        tasks = taskList,
                        onDelete = { taskId ->
                            viewModel.deleteTask(taskId)
                        },
                        onStatusChange = { task, status ->
                            viewModel.updateTaskStatus(task, status)
                        }
                    )
                    if (showDialog) {
                        AddTaskDialog(onDismiss = {
                            viewModel.onShowDialogChange()
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun StatsCards(pending: Int, inProgress: Int, done: Int) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 12.dp, end = 12.dp)
    ) {
        CurrentTasksCard(
            modifier = Modifier.weight(1f),
            number = pending,
            color = White,
            text = stringResource(string.pending)
        )
        CurrentTasksCard(
            modifier = Modifier.weight(1f),
            number = inProgress,
            color = Purple,
            text = stringResource(string.in_progress)
        )
        CurrentTasksCard(
            modifier = Modifier.weight(1f),
            number = done,
            color = Green,
            text = stringResource(string.done)
        )
    }
}
