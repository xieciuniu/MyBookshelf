package org.mobilki.mybookshelf.di

import org.koin.dsl.module
import org.mobilki.mybookshelf.data.local.DatabaseDriverFactory
import org.mobilki.mybookshelf.data.local.model.AppDatabase
import org.mobilki.mybookshelf.data.local.model.Book
import org.mobilki.mybookshelf.data.local.model.bookStatusAdapter

val databaseModule = module {
    // Provides a singleton instance of AppDatabase
    single {
        AppDatabase(
            driver = get<DatabaseDriverFactory>().createDriver(),
            BookAdapter = Book.Adapter(
                statusAdapter = bookStatusAdapter
            )
        )
    }
}