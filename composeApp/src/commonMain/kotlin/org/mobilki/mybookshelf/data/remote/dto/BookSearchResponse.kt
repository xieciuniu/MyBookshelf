package org.mobilki.mybookshelf.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookSearchResponse(
    @SerialName("numFound") val numFound: Int,
    @SerialName("start") val start: Int,
    @SerialName("numFoundExact") val numFoundExact: Boolean,
    @SerialName("docs") val docs: List<BookSearch>
)

@Serializable
data class BooksApiResponse(
    // Klucze są dynamiczne (ISBN:123456789, OCLC:123456, etc.)
    // Użyj Map<String, BookApiEntry> przy deserializacji
    val entries: Map<String, BookApiEntry> = emptyMap()
)

@Serializable
data class BookApiEntry(
    @SerialName("info_url") val infoUrl: String? = null,
    @SerialName("preview") val preview: String? = null, // "noview", "restricted", "full"
    @SerialName("preview_url") val previewUrl: String? = null,
    @SerialName("thumbnail_url") val thumbnailUrl: String? = null,
    @SerialName("details") val details: BookApiDetails? = null
)