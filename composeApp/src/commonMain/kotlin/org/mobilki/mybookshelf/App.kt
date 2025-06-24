package org.mobilki.mybookshelf

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.mobilki.mybookshelf.presentation.home.HomeScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        HomeScreen()
    }
}