package com.iamsteve.domain.util.logger

object Logger {

    private const val TAG = "IAMSTEVE"
    private var debugLogger: ((String, String) -> Unit)? = null
    private var errorLogger: ((String, String) -> Unit)? = null
    private var errorThrowableLogger: ((String, String, Throwable) -> Unit)? = null

    fun initialize(
        debugLogger: (String, String) -> Unit,
        errorLogger: (String, String) -> Unit,
        errorThrowableLogger: ((String, String, Throwable) -> Unit)
    ) {
        Logger.debugLogger = debugLogger
        Logger.errorLogger = errorLogger
        Logger.errorThrowableLogger = errorThrowableLogger
    }

    fun debug(string: String) {
        debugLogger?.invoke(TAG, string)
    }

    fun error(string: String) {
        errorLogger?.invoke(TAG, string)
    }

    fun error(string: String, throwable: Throwable) {
        errorThrowableLogger?.invoke(TAG, string, throwable)
    }
}