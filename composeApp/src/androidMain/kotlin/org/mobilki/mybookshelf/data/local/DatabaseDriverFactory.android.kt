package org.mobilki.mybookshelf.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.mobilki.mybookshelf.data.local.model.AppDatabase

actual class DatabaseDriverFactory(private val context: Context){
    actual fun createDriver():SqlDriver {
        return AndroidSqliteDriver(
            schema = AppDatabase.Schema,
            context = context,
            name = "MyBookshelf.db"
        )
    }
}