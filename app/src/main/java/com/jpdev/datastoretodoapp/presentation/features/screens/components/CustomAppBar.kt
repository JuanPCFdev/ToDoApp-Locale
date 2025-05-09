package com.jpdev.datastoretodoapp.presentation.features.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.jpdev.datastoretodoapp.R.string
import com.jpdev.datastoretodoapp.presentation.theme.BackgroundApp
import com.jpdev.datastoretodoapp.presentation.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(){
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                stringResource(string.app_name),
                color = White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundApp)
    )
}