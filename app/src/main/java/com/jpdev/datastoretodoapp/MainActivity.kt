package com.jpdev.datastoretodoapp

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.jpdev.datastoretodoapp.notifications.NotificationUtils
import com.jpdev.datastoretodoapp.presentation.navigation.NavigationWrapper
import com.jpdev.datastoretodoapp.presentation.theme.BackgroundApp
import com.jpdev.datastoretodoapp.presentation.theme.DataStoreToDoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Creating the notification channel
        NotificationUtils.createNotificationChannel(this)
        enableEdgeToEdge()
        setContent {
            var showSplash by remember { mutableStateOf(true) }

            if (showSplash) {
                SplashScreen(onTimeout = { showSplash = false })
            }else{
                DataStoreToDoAppTheme {
                    //Let's create our notification request
                    val notificationPermission = rememberPermissionState(
                        permission = Manifest.permission.POST_NOTIFICATIONS
                    )
                    LaunchedEffect(notificationPermission) {
                        //If isn't granted yet, let´s launch the notification request
                        if (!notificationPermission.status.isGranted) {
                            notificationPermission.launchPermissionRequest()
                        }
                    }
                    NavigationWrapper()
                }
            }
        }
    }

    @Composable
    fun SplashScreen(
        onTimeout: () -> Unit,
        durationMillis: Int = 250,
        offsetInitial: Dp = -200.dp,
    ) {
        var startAnimation by remember { mutableStateOf(false) }
        val offsetY: Dp by animateDpAsState(
            targetValue = if (startAnimation) 0.dp else offsetInitial,
            animationSpec = TweenSpec(
                durationMillis = durationMillis,
                easing = FastOutSlowInEasing
            )
        )
        LaunchedEffect(Unit) {
            startAnimation = true
            delay(500)
            onTimeout()
        }
        Box(
            modifier = Modifier.fillMaxSize().background(BackgroundApp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "App Logo",
                modifier = Modifier
                    .offset(y = offsetY)
                    .align(Alignment.Center)
                    .then(Modifier)
                    .size(150.dp)
            )
        }
    }

}
