package com.jpdev.datastoretodoapp.domain.model

data class Task(
    val id:String,
    val title:String,
    val description:String,
    val creationDate: Long,
    val dueDate: Long,
    val status: Status
)