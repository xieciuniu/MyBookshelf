package org.mobilki.mybookshelf

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform