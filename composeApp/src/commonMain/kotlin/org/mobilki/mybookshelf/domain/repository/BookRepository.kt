package org.mobilki.mybookshelf.domain.repository

import org.mobilki.mybookshelf.data.local.model.BookStatus

interface BookRepository {
    /**
    * Adds a new book to the data source.
    *
    * @param title The title of the book.
    * @param authorNames A list of author names for the book.
    * @param status The reading status of the book.
    * @param publishYear The year the book was published (optional).
    * @param pageCount The number of pages in the book (optional).
    * @param isbn The ISBN of the book (optional).
    * @return Result<Unit> indicating success or failure.
    */
    suspend fun addBook(
        title: String,
        authorNames: List<String>,
        status: BookStatus,
        publishYear: Int?,
        pageCount: Int?,
        isbn: String?
    ): Result<Unit>
}