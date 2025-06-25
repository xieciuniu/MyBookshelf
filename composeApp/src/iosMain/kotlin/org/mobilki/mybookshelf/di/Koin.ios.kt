package org.mobilki.mybookshelf.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.mobilki.mybookshelf.data.local.DatabaseDriverFactory

actual fun platformModule(): Module = module {
    factory { DatabaseDriverFactory() }
}