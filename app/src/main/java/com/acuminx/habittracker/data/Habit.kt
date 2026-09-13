package com.acuminx.habittracker.data

import java.util.UUID

enum class Category {
    HEALTH, WORK, STUDY, ALL
}

data class Habit(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val category: Category,
    val isCompleted: Boolean = false,
    val streak: Int = 0
)