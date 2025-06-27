package org.mobilki.mybookshelf.presentation.add_book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mobilki.mybookshelf.domain.repository.BookRepository

class AddBookViewModel (
    private val bookRepository: BookRepository
): ViewModel() {

    private val _state = MutableStateFlow(AddBookState())
    val state = _state.asStateFlow()

    fun onEvent(event: AddBookEvent) {
        when (event) {
            is AddBookEvent.OnTitleChanged -> _state.update { it.copy(title = event.title)}
            is AddBookEvent.OnAuthorChanged -> updateAuthor(event.authorId, event.name)
            is AddBookEvent.OnAddAuthorClicked -> addAuthor()
            is AddBookEvent.OnRemoveAuthorClicked -> removeAuthor(event.authorId)
            is AddBookEvent.OnStatusChanged -> _state.update { it.copy(status = event.status) }
            is AddBookEvent.OnPublishYearChanged -> _state.update { it.copy(publishYear = event.year) }
            is AddBookEvent.OnPageCountChanged -> _state.update { it.copy(pageCount = event.count) }
            is AddBookEvent.OnIsbnChanged -> _state.update { it.copy(isbn = event.isbn) }
            is AddBookEvent.OnSaveBookClicked -> saveBook()
        }
    }

    private fun addAuthor() {
       _state.update {
           it.copy(authors = it.authors + AuthorInputState())
       }
    }

    private fun updateAuthor(authorId: Long, name: String) {
        _state.update { currentState ->
            val updatedAuthors = currentState.authors.map { authorState ->
                if (authorState.id == authorId) {
                    authorState.copy(name = name)
                } else {
                    authorState
                }
            }
            currentState.copy(authors = updatedAuthors)
        }
    }

    private fun removeAuthor(authorId: Long) {
        if (_state.value.authors.size <= 1 ) return
        _state.update { currentState ->
            val updatedAuthors = currentState.authors.filter { it.id != authorId }
            currentState.copy(authors = updatedAuthors)
        }
    }

    private fun saveBook() {
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val currentState = _state.value
            val authorNames = currentState.authors.map { it.name }.filter { it.isNotBlank() }

            if (currentState.title.isBlank() || authorNames.isEmpty()) {
                _state.update { it.copy(isSaving = false, error = "Title and at least one author are required.") }
                return@launch
            }

            val result = bookRepository.addBook(
                title = currentState.title,
                authorNames = authorNames,
                status = currentState.status,
                publishYear = currentState.publishYear.toIntOrNull(),
                pageCount = currentState.pageCount.toIntOrNull(),
                isbn = currentState.isbn.takeIf { it.isNotBlank() }
            )

            result.fold(
                onSuccess = {
                    _state.update { it.copy(isSaving = false, bookSaved = true) }
                },
                onFailure = { exception ->
                    _state.update { it.copy(isSaving = false, error = exception.message ?: "An unknown error occurred") }
                }
            )
        }
    }
}