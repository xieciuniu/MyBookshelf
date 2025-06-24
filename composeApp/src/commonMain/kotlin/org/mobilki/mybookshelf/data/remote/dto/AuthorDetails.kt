package org.mobilki.mybookshelf.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorDetails(
    @SerialName("key") val key: String,
    @SerialName("name") val name: String? = null,
    @SerialName("personal_name") val personalName: String? = null,
    @SerialName("birth_date") val birthDate: String? = null,
    @SerialName("death_date") val deathDate: String? = null,
    @SerialName("bio") val bio: DescriptionField? = null,
    @SerialName("photos") val photos: List<Long>? = null,
    @SerialName("links") val links: List<ExternalLink>? = null,
    @SerialName("wikipedia") val wikipedia: String? = null,
    @SerialName("website") val website: String? = null,
    @SerialName("alternate_names") val alternateNames: List<String>? = null,
    @SerialName("fuller_name") val fullerName: String? = null,
    @SerialName("latest_revision") val latestRevision: Int? = null,
    @SerialName("revision") val revision: Int? = null,
    @SerialName("created") val created: DateTimeField? = null,
    @SerialName("last_modified") val lastModified: DateTimeField? = null
)

@Serializable
data class AuthorReference(
    @SerialName("author") val author: KeyReference,
    @SerialName("type") val type: KeyReference
)

@Serializable
data class ExternalLink(
    @SerialName("title") val title: String,
    @SerialName("url") val url: String,
    @SerialName("type") val type: KeyReference? = null
)