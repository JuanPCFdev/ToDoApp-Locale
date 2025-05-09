package com.jpdev.datastoretodoapp.presentation.features.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpdev.datastoretodoapp.presentation.theme.Gray
import com.jpdev.datastoretodoapp.presentation.theme.White

@Composable
fun CurrentTasksCard(modifier: Modifier, number: Int, color: Color, text: String) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = CardDefaults.cardColors(containerColor = Gray)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Spacer(
                Modifier
                    .height(100.dp)
                    .width(5.dp)
                    .background(color)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    number.toString(),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 25.sp,
                    color = White
                )
                Text(
                    text,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 10.sp,
                    color = White
                )
            }
        }
    }
}