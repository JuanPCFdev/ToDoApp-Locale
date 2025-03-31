package com.jpdev.datastoretodoapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TaskData(
    val id: String,
    val title: String,
    val description: String,
    val creationDate: Long,
    val dueDate: Long,
    val status: String
)
