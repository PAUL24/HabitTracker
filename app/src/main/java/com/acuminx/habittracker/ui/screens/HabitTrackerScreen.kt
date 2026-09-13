package com.acuminx.habittracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.acuminx.habittracker.data.Category
import com.acuminx.habittracker.data.Habit
import com.acuminx.habittracker.ui.components.AddHabitDialog
import com.acuminx.habittracker.ui.components.CategoryFilterRow
import com.acuminx.habittracker.ui.components.HabitItem

// STATEFUL COMPOSABLE
@Composable
fun HabitTrackerScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    // In-memory state
    val habits = remember { mutableStateListOf<Habit>() }
    var selectedFilter by remember { mutableStateOf(Category.ALL) }
    var showAddDialog by remember { mutableStateOf(false) }

    // Derived state for filtering
    val filteredHabits = remember(habits.toList(), selectedFilter) {
        if (selectedFilter == Category.ALL) habits
        else habits.filter { it.category == selectedFilter }
    }

    HabitTrackerContent(
        habits = filteredHabits,
        selectedFilter = selectedFilter,
        isDarkTheme = isDarkTheme,
        onToggleTheme = onToggleTheme,
        onFilterChange = { selectedFilter = it },
        onToggleCompletion = { habit ->
            val index = habits.indexOfFirst { it.id == habit.id }
            if (index != -1) {
                val currentStreak = habit.streak
                val newStreak = if (habit.isCompleted) currentStreak else currentStreak + 1
                habits[index] = habit.copy(
                    isCompleted = !habit.isCompleted,
                    streak = newStreak
                )
            }
        },
        onAddClick = { showAddDialog = true }
    )

    if (showAddDialog) {
        AddHabitDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, desc, category ->
                habits.add(Habit(name = name, description = desc, category = category))
                showAddDialog = false
            }
        )
    }
}

// STATELESS COMPOSABLE
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTrackerContent(
    habits: List<Habit>,
    selectedFilter: Category,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onFilterChange: (Category) -> Unit,
    onToggleCompletion: (Habit) -> Unit,
    onAddClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Habit Tracker") },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Theme"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Habit")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            CategoryFilterRow(
                selectedCategory = selectedFilter,
                onCategorySelected = onFilterChange
            )

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(habits, key = { it.id }) { habit ->
                    HabitItem(
                        habit = habit,
                        onToggleCompletion = onToggleCompletion
                    )
                }
            }
        }
    }
}