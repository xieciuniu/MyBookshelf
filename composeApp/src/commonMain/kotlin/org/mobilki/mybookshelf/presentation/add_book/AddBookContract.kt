package org.mobilki.mybookshelf.presentation.add_book

import com.benasher44.uuid.*
import org.mobilki.mybookshelf.data.local.model.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class AddBookState(
    val title: String = "",
    val authors: List<AuthorInputState> = listOf(AuthorInputState()),
    val status: BookStatus = BookStatus.TO_READ,
    val publishYear: String = "",
    val pageCount: String = "",
    val isbn: String = "",
    val isSaving: Boolean = false,
    val error: String? = null,
    val bookSaved: Boolean = false
)

// Unique state for each author input field
data class AuthorInputState @OptIn(ExperimentalUuidApi::class) constructor(
    val name: String = "",
    val id: String = uuid4().toString()
)

sealed interface AddBookEvent {
    data class OnTitleChanged(val title: String) : AddBookEvent
    data class OnAuthorChanged(val authorId: String, val name: String): AddBookEvent
    object OnAddAuthorClicked: AddBookEvent
    data class OnRemoveAuthorClicked(val authorId: String) : AddBookEvent
    data class OnStatusChanged(val status: BookStatus) : AddBookEvent
    data class OnPublishYearChanged(val year: String) : AddBookEvent
    data class OnPageCountChanged(val count: String) : AddBookEvent
    data class OnIsbnChanged(val isbn: String) : AddBookEvent
    object OnSaveBookClicked : AddBookEvent
    object OnNavigatedAway: AddBookEvent
}