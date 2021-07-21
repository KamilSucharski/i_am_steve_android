package com.iamsteve.android.di

object PresentationModule {

    fun provide() = listOf(
        utilModule,
        operationModule,
        presenterModule,
        errorHandlerModule
    )
}