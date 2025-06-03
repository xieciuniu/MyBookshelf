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




}