package com.iamsteve.domain.util.abstraction

interface Logger {
    fun debug(string: String)
    fun warning(string: String)
    fun warning(string: String, throwable: Throwable)
    fun error(string: String)
    fun error(string: String, throwable: Throwable)
}