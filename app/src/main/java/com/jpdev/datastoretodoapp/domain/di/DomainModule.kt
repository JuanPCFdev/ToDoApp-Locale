package com.jpdev.datastoretodoapp.domain.di

import com.jpdev.datastoretodoapp.domain.repository.TaskRepository
import com.jpdev.datastoretodoapp.domain.usecases.AddTaskUseCase
import com.jpdev.datastoretodoapp.domain.usecases.DeleteTaskUseCase
import com.jpdev.datastoretodoapp.domain.usecases.GetTasksUseCase
import com.jpdev.datastoretodoapp.domain.usecases.ScheduleTaskExpiryUseCase
import com.jpdev.datastoretodoapp.domain.usecases.UpdateTaskStatusUseCase
import com.jpdev.datastoretodoapp.domain.usecases.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetTasksUseCase(repository: TaskRepository): GetTasksUseCase =
        GetTasksUseCase(repository)

    @Provides
    fun provideAddTaskUseCase(repository: TaskRepository): AddTaskUseCase =
        AddTaskUseCase(repository)

    @Provides
    fun provideDeleteTaskUseCase(repository: TaskRepository): DeleteTaskUseCase =
        DeleteTaskUseCase(repository)

    @Provides
    fun provideUpdateTaskUseCase(repository: TaskRepository): UpdateTaskUseCase =
        UpdateTaskUseCase(repository)

    @Provides
    fun provideUpdateTaskStatusUseCase(repository: TaskRepository): UpdateTaskStatusUseCase =
        UpdateTaskStatusUseCase(repository)



}