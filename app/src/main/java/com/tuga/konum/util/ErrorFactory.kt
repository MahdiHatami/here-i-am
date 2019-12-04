package com.tuga.konum.util

interface ErrorFactory {
    fun createEmptyErrorMessage(): String

    fun createApiErrorMessage(e: Exception): String
}
