package com.jpdev.datastoretodoapp.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.jpdev.datastoretodoapp.presentation.theme.*
import com.jpdev.datastoretodoapp.R
import com.jpdev.datastoretodoapp.domain.model.Status
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.first

class TaskWidget() : GlanceAppWidget() {

    override val stateDefinition = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId,
    ) {

        val entryPoint = EntryPointAccessors.fromApplication(
            context,
            WidgetEntryPoint::class.java
        )
        val getTasksUseCase = entryPoint.getTasksUseCase()

        val tasks = getTasksUseCase.invoke().first()

        val counts = tasks.groupingBy { it.status }.eachCount()
        val pending = counts[Status.PENDING] ?: 0
        val inProgress = counts[Status.IN_PROGRESS] ?: 0
        val done = counts[Status.DONE] ?: 0
        provideContent {
            WidgetContent(pending, inProgress, done)
        }
    }

    @Composable
    private fun WidgetContent(pending: Int, inProgress: Int, done: Int) {
        Column(
            modifier = GlanceModifier.fillMaxSize().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = GlanceModifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StatusItem(R.drawable.ic_pending, "Pendiente", pending, Gray)
                Spacer(modifier = GlanceModifier.width(3.dp))
                StatusItem(R.drawable.ic_progress, "En progreso", inProgress, Purple)
                Spacer(modifier = GlanceModifier.width(3.dp))
                StatusItem(R.drawable.ic_done, "Hecho", done, Green)
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @Composable
    private fun StatusItem(icon: Int, label: String, count: Int, color: Color) {
        Box(
            modifier = GlanceModifier.width(75.dp).background(color).cornerRadius(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = GlanceModifier.padding(4.dp)
            ) {
                Image(provider = ImageProvider(icon), contentDescription = null)
                Spacer()
                Text(
                    "$count",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorProvider(R.color.white)
                    )
                )
                Spacer()
                Text(
                    label,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = ColorProvider(R.color.white)
                    )
                )
            }
        }
    }

}