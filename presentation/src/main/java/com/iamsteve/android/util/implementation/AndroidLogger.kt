package com.iamsteve.android.util.implementation

import android.util.Log
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.abstraction.Logger

class AndroidLogger : Logger {

    override fun debug(string: String) {
        Log.d(Consts.TAG, string)
    }

    override fun error(string: String) {
        Log.e(Consts.TAG, string)
    }

    override fun error(string: String, throwable: Throwable) {
        Log.e(Consts.TAG, string, throwable)
    }
}