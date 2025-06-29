package org.mobilki.mybookshelf.data.repository

import kotlinx.coroutines.flow.*
import org.mobilki.mybookshelf.data.local.model.*
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

    override fun getAllBooks(): Flow<List<Book>> {
        fun getAllBooks(): Flow<List<Book>> {
            // This query comes from the 'getAllBooks:' label in your .sq file
            return queries.getAllBooks()
                .asFlow() // Convert the SQLDelight query to a Flow
                .mapToList() // Map it to a list of database entities
                .map { dbBooks ->
                    // Map the raw database books to our clean domain models
                    dbBooks.map { dbBook ->
                        // This is a placeholder for authors, we'll implement this properly later
                        val authors = queries.getAuthorsForBook(dbBook.id).executeAsList()
                        org.mobilki.mybookshelf.domain.model.Book(
                            id = dbBook.id,
                            title = dbBook.title,
                            authors = authors.map { it.name },
                            status = BookStatus.valueOf(dbBook.status.name),
                            coverPath = dbBook.coverPath,
                            publishYear = dbBook.publishYear?.toInt(),
                            pageCount = dbBook.pageCount?.toInt(),
                            isbn = dbBook.isbn
                        )
                    }
                }
        }

    }
}