package com.iamsteve.android

import android.app.Application
import com.iamsteve.android.di.PresentationModule
import com.iamsteve.data.di.DataModule
import io.reactivex.rxjava3.exceptions.UndeliverableException
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initRxJava()
        initKoin()
    }

    private fun initRxJava() {
        RxJavaPlugins.setErrorHandler { throwable: Throwable ->
            if (throwable is UndeliverableException) {
                return@setErrorHandler
            }
            Thread
                .currentThread()
                .uncaughtExceptionHandler
                ?.uncaughtException(Thread.currentThread(), throwable)
        }
    }

    private fun initKoin() {
        val presentationModules = PresentationModule.provide()
        val dataModules = DataModule.provide(
            DataModule.Parameters(
                apiUrl = BuildConfig.API_URL,
                isNetworkLoggingAllowed = BuildConfig.ALLOW_NETWORK_LOGGING
            )
        )

        startKoin {
            androidContext(this@App)
            modules(presentationModules + dataModules)
        }
    }
}