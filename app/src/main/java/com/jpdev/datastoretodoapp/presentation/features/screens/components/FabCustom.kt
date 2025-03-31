package com.jpdev.datastoretodoapp.presentation.features.screens.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jpdev.datastoretodoapp.presentation.theme.Blue
import com.jpdev.datastoretodoapp.presentation.theme.White
import com.jpdev.datastoretodoapp.R

@Composable
fun FabCustom(onClick:()-> Unit){
    FloatingActionButton(
        onClick = onClick,
        containerColor = Blue,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation()
    ) {
        Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add), tint = White)
    }
}