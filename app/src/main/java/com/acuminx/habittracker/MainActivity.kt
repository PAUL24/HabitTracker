package com.acuminx.habittracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.acuminx.habittracker.ui.screens.HabitTrackerScreen
import com.acuminx.habittracker.ui.theme.HabitTrackerTheme

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.acuminx.habittracker.data.HabitDatabase
import com.acuminx.habittracker.viewmodel.HabitViewModel
import com.acuminx.habittracker.viewmodel.HabitViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Database and DAO
        val database = HabitDatabase.getDatabase(this)
        val dao = database.habitDao()
        val factory = HabitViewModelFactory(dao)

        setContent {
            val systemTheme = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(systemTheme) }

            // Get ViewModel instance scoped to this activity
            val viewModel: HabitViewModel = viewModel(factory = factory)

            HabitTrackerTheme(darkTheme = isDarkTheme) {
                HabitTrackerScreen(
                    viewModel = viewModel,
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}

// Minimal Material 3 Theme wrapper with Dynamic Color support (API 31+)
@Composable
fun HabitTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        darkTheme -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            dynamicDarkColorScheme(context)
        } else {
            TODO("VERSION.SDK_INT < S")
        }

        else -> dynamicLightColorScheme(context)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}