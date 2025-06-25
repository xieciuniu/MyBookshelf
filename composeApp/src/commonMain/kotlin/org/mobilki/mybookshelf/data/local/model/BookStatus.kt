package org.mobilki.mybookshelf.data.local.model

import app.cash.sqldelight.ColumnAdapter

enum class BookStatus {
    READING,
    TO_READ,
    COMPLETED,
    ABANDONED
}

val bookStatusAdapter = object : ColumnAdapter<BookStatus, String> {
    override fun decode(databaseValue: String): BookStatus {
        return BookStatus.valueOf(databaseValue)
    }

    override fun encode(value: BookStatus): String {
        return value.name
    }
}