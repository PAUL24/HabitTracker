package com.acuminx.habittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.acuminx.habittracker.data.Category
import com.acuminx.habittracker.data.Habit
import com.acuminx.habittracker.data.HabitDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HabitViewModel(private val dao: HabitDao) : ViewModel() {

    // stateIn converts the cold Flow from Room into a hot StateFlow for the UI
    val habits: StateFlow<List<Habit>> = dao.getAllHabits()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addHabit(name: String, description: String, category: Category) {
        viewModelScope.launch {
            dao.insertHabit(
                Habit(name = name, description = description, category = category)
            )
        }
    }

    fun toggleHabitCompletion(habit: Habit) {
        viewModelScope.launch {
            val newStreak = if (habit.isCompleted) habit.streak else habit.streak + 1
            dao.updateHabit(
                habit.copy(
                    isCompleted = !habit.isCompleted,
                    streak = newStreak
                )
            )
        }
    }
}

// Factory to inject the DAO into the ViewModel
class HabitViewModelFactory(private val dao: HabitDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}