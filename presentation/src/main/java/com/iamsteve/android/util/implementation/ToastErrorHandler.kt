package com.iamsteve.android.util.implementation

import android.app.Activity
import android.widget.Toast
import com.iamsteve.domain.util.error.ErrorHandler
import com.iamsteve.domain.util.Logger

class ToastErrorHandler(
    private val activitySupplier: () -> Activity?,
    private val logger: Logger
) : ErrorHandler {

    override fun handleError(throwable: Throwable): Boolean {
        activitySupplier.invoke()?.let { activity ->
            logger.error("ToastErrorHandler handled error", throwable)
            activity.runOnUiThread {
                val message = throwable.message ?: throwable.javaClass.simpleName
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}