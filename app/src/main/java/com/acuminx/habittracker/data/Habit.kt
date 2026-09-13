package com.acuminx.habittracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.UUID

enum class Category {
    HEALTH, WORK, STUDY, ALL
}

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val category: Category,
    val isCompleted: Boolean = false,
    val streak: Int = 0
)

// Converters so Room knows how to save/load the Category enum
class HabitTypeConverters {
    @TypeConverter
    fun fromCategory(category: Category): String = category.name

    @TypeConverter
    fun toCategory(name: String): Category = Category.valueOf(name)
}