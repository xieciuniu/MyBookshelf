package org.mobilki.mybookshelf

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.mobilki.mybookshelf.di.initKoin

class MyBookshelfApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            // This gives Koin the Android application context
            androidContext(this@MyBookshelfApplication)
        }
    }
}