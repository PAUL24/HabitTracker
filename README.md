# HabitTracker

A simple and efficient Android application to help users build and maintain healthy habits.

## 🚀 Features

- **Habit Tracking:** Easily create, update, and track your daily habits.
- **Local Persistence:** Your data stays on your device using Android's Room database.
- **Modern UI:** Built with Jetpack Compose and Material 3 for a sleek, responsive experience.
- **Offline First:** Fully functional without an internet connection.

## 🛠 Tech Stack

- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Architecture:** MVVM (Model-View-ViewModel)
- **Database:** [Room](https://developer.android.com/training/data-storage/room)
- **Dependency Management:** Gradle Version Catalog (`libs.versions.toml`)
- **Annotation Processing:** [KSP (Kotlin Symbol Processing)](https://kotlinlang.org/docs/ksp-overview.html)

## 🏗 Project Structure

The project follows a standard Android multi-module architecture (currently focused on the `:app` module):

- `data/`: Contains Room entities, DAOs, and database configuration.
- `ui/`: Houses Compose screens, components, and theme definitions.
- `viewmodel/`: Manages UI state and business logic.

## 🚦 Getting Started

### Prerequisites

- Android Studio (latest version recommended)
- JDK 17 or higher
- Android SDK 24+ (Minimum SDK)

### Installation

1. Open the project in Android Studio.
2. Sync Project with Gradle Files.
3. Run the app on an emulator or a physical device.
