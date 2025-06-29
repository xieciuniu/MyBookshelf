package org.mobilki.mybookshelf

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.mobilki.mybookshelf.presentation.add_book.*

import org.mobilki.mybookshelf.presentation.home.HomeScreen

sealed class Screen {
    object Home : Screen()
    object AddBook : Screen()
}

@Composable
@Preview
fun App() {
    MaterialTheme {
       var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

       when (currentScreen) {
           is Screen.Home -> HomeScreen(
               onNavigateToAddBook = { currentScreen = Screen.AddBook }
           )
           is Screen.AddBook -> AddBookRoute(
               onBookSaved = { currentScreen = Screen.Home }
           )
       }
    }
}