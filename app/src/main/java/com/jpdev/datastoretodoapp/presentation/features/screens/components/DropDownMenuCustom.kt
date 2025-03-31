package com.jpdev.datastoretodoapp.presentation.features.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jpdev.datastoretodoapp.domain.model.Status
import com.jpdev.datastoretodoapp.presentation.theme.Black
import com.jpdev.datastoretodoapp.presentation.theme.Gray
import com.jpdev.datastoretodoapp.presentation.theme.White
import com.jpdev.datastoretodoapp.R

@Composable
fun DropDownCustom(
    currentStatus: Status,
    onStatusSelected: (Status) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    var maxWidth by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        TextButton(
            onClick = {
                expanded = !expanded
            }
        ) {
            Text(
                text = when (currentStatus.name) {
                    "PENDING" -> stringResource(R.string.pending)
                    "IN_PROGRESS" -> stringResource(R.string.in_progress)
                    "DONE" -> stringResource(R.string.done)
                    else -> ""
                },
                color = White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.widthIn(min = maxWidth.dp),
            containerColor = Gray
        ) {
            Column {

                Status.entries.forEach { status ->
                    //FAKE TEXT FOR THE SIZE
                    Text(
                        text = status.name,
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                val width = coordinates.size.width
                                if (width > maxWidth) maxWidth = width
                            }
                            .alpha(0f)
                    )
                    //REAL TEXT
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = when (status.name) {
                                    "PENDING" -> stringResource(R.string.pending)
                                    "IN_PROGRESS" -> stringResource(R.string.in_progress)
                                    "DONE" -> stringResource(R.string.done)
                                    else -> ""
                                },
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start,
                                color = Black,
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        onClick = {
                            expanded = false
                            onStatusSelected(status)
                        }
                    )

                }
            }

        }

    }
}