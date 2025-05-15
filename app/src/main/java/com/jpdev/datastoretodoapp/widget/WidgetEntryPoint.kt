package com.jpdev.datastoretodoapp.widget

import com.jpdev.datastoretodoapp.domain.usecases.GetTasksUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetEntryPoint {
    fun getTasksUseCase(): GetTasksUseCase
}