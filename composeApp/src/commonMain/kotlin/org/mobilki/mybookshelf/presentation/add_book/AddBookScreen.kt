package org.mobilki.mybookshelf.presentation.add_book

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddBookRoute(
    onBookSaved: () -> Unit,
    viewModel: AddBookViewModel = koinViewModel<AddBookViewModel>()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.bookSaved) {
        if (state.bookSaved) {
            onBookSaved()
        }
    }

//    AddBookScreen(
//
//    )

}

