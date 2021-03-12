package com.iamsteve.domain.util

interface Logger {
    fun debug(string: String)
    fun error(string: String)
    fun error(string: String, throwable: Throwable)
}