package org.mobilki.mybookshelf.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookSearch(
    @SerialName("key") val key: String,
    @SerialName("title") val title: String? = null,
    @SerialName("author_name") val author: List<String>? = null,
    @SerialName("author_key") val authorKey: List<String>? = null,
    @SerialName("first_publish_year") val firstPublishYear: Int? = null,
    @SerialName("isbn") val isbn: List<String>? = null,
    @SerialName("publisher") val publisher: List<String>? = null,
    @SerialName("cover_i") val coverID: Long? = null,
    @SerialName("language") val language: List<String>? = null,
    @SerialName("number_of_pages_median") val numberOfPagesMedian: Int? = null,
    @SerialName("publish_year") val publishYear: List<Int>? = null,
    @SerialName("subject") val subjects: List<String>? = null,
    @SerialName("ratings_average") val ratingsAverage: Double? = null,
    @SerialName("ratings_count") val ratingsCount: Int? = null,
    @SerialName("want_to_read_count") val wantToReadCount: Int? = null,
    @SerialName("currently_reading_count") val currentlyReadingCount: Int? = null,
    @SerialName("already_read_count") val alreadyReadCount: Int? = null,
)

@Serializable
data class BookDetails(
    @SerialName("key") val key: String,
    @SerialName("title") val title: String? = null,
    @SerialName("description") val description: DescriptionField? = null,
    @SerialName("covers") val covers: List<Long>? = null,
    @SerialName("subjects") val subjects: List<String>? = null,
    @SerialName("subject_people") val subjectPeople: List<String>? = null,
    @SerialName("subject_places") val subjectPlaces: List<String>? = null,
    @SerialName("subject_times") val subjectTimes: List<String>? = null,
    @SerialName("authors") val authors: List<AuthorReference>? = null,
    @SerialName("first_publish_date") val firstPublishDate: String? = null,
    @SerialName("latest_revision") val latestRevision: Int? = null,
    @SerialName("revision") val revision: Int? = null,
    @SerialName("created") val created: DateTimeField? = null,
    @SerialName("last_modified") val lastModified: DateTimeField? = null
)

@Serializable
data class BookApiDetails(
    @SerialName("key") val key: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("authors") val authors: List<AuthorReference>? = null,
    @SerialName("publish_date") val publishDate: String? = null,
    @SerialName("publishers") val publishers: List<String>? = null,
    @SerialName("isbn_10") val isbn10: List<String>? = null,
    @SerialName("isbn_13") val isbn13: List<String>? = null,
    @SerialName("number_of_pages") val numberOfPages: Int? = null,
    @SerialName("covers") val covers: List<Long>? = null
)

@Serializable
sealed class DescriptionField {
    data class Text(val value: String) : DescriptionField()
    data class Object(val type: String, val value: String) : DescriptionField()
}

@Serializable
data class DateTimeField(
    @SerialName("type") val type: String,
    @SerialName("value") val value: String
)

@Serializable
data class KeyReference(
    @SerialName("key") val key: String
)