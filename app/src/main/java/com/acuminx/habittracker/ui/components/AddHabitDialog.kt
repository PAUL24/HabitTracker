package com.acuminx.habittracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.acuminx.habittracker.data.Category
import com.acuminx.habittracker.ui.theme.HabitTrackerTheme

@Composable
fun AddHabitDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, desc: String, category: Category) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(Category.HEALTH) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Habit") },
        text = {
            AddHabitDialogContent(
                name = name,
                onNameChange = { name = it },
                description = description,
                onDescriptionChange = { description = it },
                category = category,
                onCategoryChange = { category = it }
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(name, description, category) },
                enabled = name.isNotBlank()
            ) { Text("Add") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun AddHabitDialogContent(
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    category: Category,
    onCategoryChange: (Category) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Habit Name") },
            singleLine = true
        )
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") }
        )
        Text("Category", style = MaterialTheme.typography.labelLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Category.entries.filter { it != Category.ALL }.forEach { cat ->
                FilterChip(
                    selected = category == cat,
                    onClick = { onCategoryChange(cat) },
                    label = { Text(cat.name) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddHabitDialogPreview() {
    HabitTrackerTheme {
        AddHabitDialogContent(
            name = "Morning Run",
            onNameChange = {},
            description = "Run for 30 minutes",
            onDescriptionChange = {},
            category = Category.HEALTH,
            onCategoryChange = {}
        )
    }
}

