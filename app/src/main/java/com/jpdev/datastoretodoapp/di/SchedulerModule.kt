package com.jpdev.datastoretodoapp.di

import com.jpdev.datastoretodoapp.data.scheduler.TaskScheduler
import com.jpdev.datastoretodoapp.data.scheduler.WorkManagerTaskScheduler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SchedulerModule {

    @Binds
    @Singleton
    abstract fun bindTaskScheduler(
        workManagerTaskScheduler: WorkManagerTaskScheduler
    ): TaskScheduler

}