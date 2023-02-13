package com.iamsteve.android.di

import com.iamsteve.domain.util.di.KoinModule
import com.iamsteve.domain.util.di.presenterModule

object PresentationModule : KoinModule<PresentationModule.Parameters> {

    class Parameters

    override fun provide(parameters: Parameters) = listOf(
        utilModule,
        operationModule,
        presenterModule,
        errorHandlerModule
    )
}