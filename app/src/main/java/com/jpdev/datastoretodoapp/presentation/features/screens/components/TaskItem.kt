package com.jpdev.datastoretodoapp.presentation.features.screens.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpdev.datastoretodoapp.R
import com.jpdev.datastoretodoapp.domain.model.Status
import com.jpdev.datastoretodoapp.domain.model.Task
import com.jpdev.datastoretodoapp.presentation.features.screens.utils.customFormatDate
import com.jpdev.datastoretodoapp.presentation.theme.Gray
import com.jpdev.datastoretodoapp.presentation.theme.Green
import com.jpdev.datastoretodoapp.presentation.theme.Purple
import com.jpdev.datastoretodoapp.presentation.theme.White

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    task: Task,
    onStatusChange: (String, Status) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (task.status) {
                Status.PENDING -> Gray
                Status.IN_PROGRESS -> Purple
                Status.DONE -> Green
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Box{
                Text(
                    text = task.title.uppercase(),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = White
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = task.description,
                fontSize = 14.sp,
                color = White
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.due) + " ${customFormatDate(task.dueDate)}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = White
                )
                DropDownCustom(
                    currentStatus = task.status,
                    onStatusSelected = { newStatus ->
                        onStatusChange(task.id, newStatus)
                    }
                )
            }
        }
    }
}