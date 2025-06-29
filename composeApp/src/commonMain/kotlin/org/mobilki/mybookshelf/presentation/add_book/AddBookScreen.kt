package org.mobilki.mybookshelf.presentation.add_book

import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.mobilki.mybookshelf.data.local.model.*

@Composable
fun AddBookRoute(
    onBookSaved: () -> Unit,
    viewModel: AddBookViewModel = koinViewModel<AddBookViewModel>()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.bookSaved) {
        if (state.bookSaved) {
            onBookSaved()
            viewModel.onEvent(AddBookEvent.OnNavigatedAway)
        }
    }

    AddBookScreen(
        state = state,
        onEvent = viewModel::onEvent
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(
    state: AddBookState,
    onEvent: (AddBookEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add New Book") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title Field
            item {
                OutlinedTextField(
                    value = state.title,
                    onValueChange = { onEvent(AddBookEvent.OnTitleChanged(it)) },
                    label = { Text("Title*") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = state.error?.contains("Title") == true
                )
            }

            //Dynamic Author fields
            items(state.authors, key = { it.id }) { authorState ->
                AuthorInput(
                    authorName = authorState.name,
                    onNameChange = { onEvent(AddBookEvent.OnAuthorChanged(authorState.id, it)) },
                    onRemove = { onEvent(AddBookEvent.OnRemoveAuthorClicked(authorState.id)) },
                    canBeRemoved = state.authors.size > 1
                )
            }

            // Add author button
            item {
                Button(
                    onClick = { onEvent(AddBookEvent.OnAddAuthorClicked) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Author")
                    Spacer(Modifier.width(4.dp))
                    Text("Add Author")
                }
            }

            // Book status dropdown
            item {
                StatusDropDown(
                    selectedStatus = state.status,
                    onStatusSelected = { onEvent(AddBookEvent.OnStatusChanged(it)) }
                )

            }

            // Optional Fields in a Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = state.publishYear,
                        onValueChange = { onEvent(AddBookEvent.OnPublishYearChanged(it)) },
                        label = { Text("Year") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = state.pageCount,
                        onValueChange = { onEvent(AddBookEvent.OnPageCountChanged(it)) },
                        label = { Text("Pages") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // ISBN Field
            item {
                OutlinedTextField(
                    value = state.isbn,
                    onValueChange = { onEvent(AddBookEvent.OnIsbnChanged(it)) },
                    label = { Text("ISBN") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Save Button
            item {
                Button(
                    onClick = { onEvent(AddBookEvent.OnSaveBookClicked) },
                    enabled = !state.isSaving,
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text("Save Book")
                    }
                }
            }

            // Error Message
            if (state.error != null) {
                item {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun AuthorInput(
    authorName: String,
    onNameChange: (String) -> Unit,
    onRemove: () -> Unit,
    canBeRemoved: Boolean
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        OutlinedTextField(
            value = authorName,
            onValueChange = onNameChange,
            label = { Text("Author*")},
            modifier = Modifier.weight(1f)
        )
        if (canBeRemoved) {
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Remove Author")
            }
        }
    }
}

// A dedicated composable for the status dropdown.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusDropDown(
    selectedStatus: BookStatus,
    onStatusSelected: (BookStatus) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedStatus.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() },
            onValueChange = {},
            readOnly = true,
            label = { Text("Status") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            BookStatus.values().forEach { status ->
                DropdownMenuItem(
                    text = { Text(status.name.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }) },
                    onClick = {
                        onStatusSelected(status)
                        expanded = false
                    }
                )
            }
        }
    }
}