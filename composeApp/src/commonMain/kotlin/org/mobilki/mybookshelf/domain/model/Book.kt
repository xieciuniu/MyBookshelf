package org.mobilki.mybookshelf.domain.model

data class Book(
    val id: Long,
    val title: String,
    val authors: List<String>,
    val status: BookStatus,
    val coverPath: String?,
    val publishYear: Int?,
    val pageCount: Int?,
    val isbn: String?
)

enum class BookStatus {
    READING,
    TO_READ,
    COMPLETED,
    ABANDONED
}