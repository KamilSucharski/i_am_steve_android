package com.iamsteve.domain.util.error

interface ErrorHandler {
    fun handleError(throwable: Throwable): Boolean
}