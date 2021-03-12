package com.iamsteve.android.di

import android.app.Activity
import com.iamsteve.android.util.implementation.ToastErrorHandler
import org.koin.dsl.module

val errorHandlerModule = module {
    factory { (activitySupplier: () -> Activity?) -> ToastErrorHandler(activitySupplier, get()) }
}