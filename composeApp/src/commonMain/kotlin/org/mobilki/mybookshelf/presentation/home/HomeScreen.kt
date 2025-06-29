package org.mobilki.mybookshelf.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch


import org.mobilki.mybookshelf.data.remote.dto.BookSearch
import org.mobilki.mybookshelf.data.remote.ApiService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToAddBook: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var searchResult by remember { mutableStateOf<List<BookSearch>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Corouine scope for launching network requests
    val coroutineScope = rememberCoroutineScope()

    val apiService = remember { ApiService() }

    fun performSearch() {
        if (searchQuery.isBlank()) {
            searchResult = emptyList()
            errorMessage = null
            return
        }
        coroutineScope.launch {
            isLoading = true
            errorMessage = null
            val resultFromApi = apiService.searchBooks(searchQuery)
            resultFromApi.fold(
                onSuccess = { books ->
                    searchResult = books
                    if (books.isEmpty()) {
                        errorMessage = "No books found for \"$searchQuery\""
                    }
                },
                onFailure = { exception ->
                    searchResult = emptyList()
                    errorMessage = "Failed to fetch books: ${exception.message ?: "An unknown error occurred"}"
                    println("Search failed: ${exception.stackTraceToString()}")
                }
            )

            isLoading = false
        }
    }

    Scaffold(
      topBar = {
          TopAppBar(title = { Text("My Bookshelf") })
      },

        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddBook) {
                Icon(Icons.Default.Add, contentDescription = "Add Book")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            //Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search...")},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(8.dp))

            //Search button
            Button(onClick = { performSearch() }) {
                Text("Search")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Result Area
            if (isLoading) {
                CircularProgressIndicator()
            } else if (errorMessage != null) {
                Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
            } else {
                if (searchResult.isEmpty() && searchQuery.isNotBlank() && !isLoading) {
                    //Text("No result found for \"$searchQuery\"")
                } else {
                    LazyColumn (modifier = Modifier.fillMaxSize()) {
                        items(searchResult) { book ->
                            SingleBook(book, apiService.getBookCoverUrl(book, "M"))
                            HorizontalDivider()
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun SingleBook(book: BookSearch, bookCoverUrl: String?) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(width = 60.dp, height = 90.dp)
                .padding(end = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (bookCoverUrl != null) {
                val painter = rememberAsyncImagePainter(model = bookCoverUrl)
                Image(
                    painter = painter, // or state.painter
                    contentDescription = "${book.title} cover",
                    contentScale = ContentScale.Crop, // Or your preferred scale
                    modifier = Modifier.fillMaxSize()
                )

            } else {
                println("COIL_DEBUG: bookCoverUrl is null for book: ${book.title}")
            }

            if (bookCoverUrl != null){
                SubcomposeAsyncImage(
                    model = bookCoverUrl,
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = "${book.title} cover",
                )
            } else {
                Text("No cover")
            }
        }


        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(
                text = book.title ?: "No title",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = book.author.toString(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}