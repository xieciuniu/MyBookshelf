package org.mobilki.mybookshelf.di

import org.koin.compose.viewmodel.dsl.*
import org.koin.dsl.module
import org.mobilki.mybookshelf.data.local.DatabaseDriverFactory
import org.mobilki.mybookshelf.data.local.model.AppDatabase
import org.mobilki.mybookshelf.data.local.model.Book
import org.mobilki.mybookshelf.data.local.model.bookStatusAdapter
import org.mobilki.mybookshelf.data.repository.*
import org.mobilki.mybookshelf.domain.repository.*
import org.mobilki.mybookshelf.presentation.add_book.*

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

// new module contains repository and viewmodel definitions
val appDataModule = module {
    single<BookRepository> { BookRepositoryImpl(get()) }
    factory { AddBookViewModel(get()) }
}

// A combined list of all our modules for easy setup
val allModules = listOf(databaseModule, appDataModule)