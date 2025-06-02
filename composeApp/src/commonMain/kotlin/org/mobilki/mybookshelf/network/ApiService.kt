package org.mobilki.mybookshelf.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.mobilki.mybookshelf.model.BookSearch
import org.mobilki.mybookshelf.model.BookSearchResponse

class ApiService {
    private val searchBookUrl = "https://openlibrary.org/search.json?q="

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun searchBooks(
        query: String,
        limit: Int = 20
    ): Result<List<BookSearch>> {
        val correctQuery = query.replace(" ", "+")
        return try {
            val response: HttpResponse = httpClient.get("$searchBookUrl$correctQuery")
            print("$searchBookUrl$correctQuery")
            if (response.status.isSuccess()) {
                val searchResponse = response.body<BookSearchResponse>()
                Result.success(searchResponse.docs)
            } else {
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getBookCoverUrl(
        book: BookSearch,
        size: String
    ): String? {
        return when {
            book.coverID != null && book.coverID > 0 -> {
                "https://covers.openlibrary.org/b/id/${book.coverID}-${size}.jpg"
            }
            !book.isbn.isNullOrEmpty() -> {
                "https://covers.openlibrary.org/b/isbn/${book.isbn[0]}-${size}.jpg"
            }
            else -> {
                null
            }
        }
    }


}