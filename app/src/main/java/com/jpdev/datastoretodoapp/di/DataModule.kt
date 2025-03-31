package com.jpdev.datastoretodoapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.jpdev.datastoretodoapp.data.mapper.TaskMapper
import com.jpdev.datastoretodoapp.data.repository.TaskRepositoryImpl
import com.jpdev.datastoretodoapp.data.source.TaskLocalDataSource
import com.jpdev.datastoretodoapp.data.source.TaskLocalDataSourceImpl
import com.jpdev.datastoretodoapp.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @Provides
    fun provideDataSource(
        dataStore: DataStore<Preferences>,
        json: Json
    ): TaskLocalDataSource = TaskLocalDataSourceImpl(dataStore, json)

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tasks_store")

    @Provides
    @Singleton
    fun provideTaskMapper(): TaskMapper = TaskMapper()

    @Provides
    @Singleton
    fun provideTaskRepository(
        dataSource: TaskLocalDataSource,
        mapper: TaskMapper
    ): TaskRepository = TaskRepositoryImpl(dataSource, mapper)
}