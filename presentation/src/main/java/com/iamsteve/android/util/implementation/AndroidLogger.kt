package com.iamsteve.android.util.implementation

import android.util.Log
import com.iamsteve.domain.util.Logger

class AndroidLogger : Logger {

    companion object {
        private const val TAG = "IAMSTEVE"
    }

    override fun debug(string: String) {
        Log.d(TAG, string)
    }

    override fun error(string: String) {
        Log.e(TAG, string)
    }

    override fun error(string: String, throwable: Throwable) {
        Log.e(TAG, string, throwable)
    }
}