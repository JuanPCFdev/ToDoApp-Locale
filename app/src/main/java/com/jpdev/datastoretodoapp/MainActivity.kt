package com.jpdev.datastoretodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jpdev.datastoretodoapp.presentation.navigation.NavigationWrapper
import com.jpdev.datastoretodoapp.presentation.theme.DataStoreToDoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DataStoreToDoAppTheme {
                NavigationWrapper()
            }
        }
    }
}
