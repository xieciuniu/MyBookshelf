package org.mobilki.mybookshelf.data.repository

import org.mobilki.mybookshelf.data.local.model.AppDatabase
import org.mobilki.mybookshelf.data.local.model.BookStatus
import org.mobilki.mybookshelf.domain.repository.BookRepository

class BookRepositoryImpl(
    private val db: AppDatabase
) : BookRepository {
    private val queries = db.appDatabaseQueries

    override suspend fun addBook(
        title: String,
        authorNames: List<String>,
        status: BookStatus,
        publishYear: Int?,
        pageCount: Int?,
        isbn: String?
    ): Result<Unit> = runCatching{

        queries.transaction {
            // Step 1: Insert the book and get its new ID
            queries.insertBook(
                title = title,
                isbn = isbn,
                publishYear = publishYear?.toLong(),
                pageCount = pageCount?.toLong(),
                coverPath = null, // TODO: handle cover images
                status = status
            )

            // Get newest book id
            val bookId = queries.newestBookId().executeAsOne()

            // Step 2: Insert authors and link them to the book
            authorNames.forEach { authorName ->
                queries.insertAuthor(name = authorName)
                val authorId = queries.getAuthorIdByName(name = authorName).executeAsOne()
                queries.linkAuthorToBook(bookId = bookId, authorId = authorId)
            }
        }
    }
}