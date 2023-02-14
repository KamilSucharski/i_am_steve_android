package com.iamsteve.android.util.di

import com.iamsteve.domain.util.di.KoinModule

object PresentationKoinModule : KoinModule<PresentationKoinModule.Parameters> {

    class Parameters

    override fun provide(parameters: Parameters) = listOf(
        utilModule,
        presentationDataSourceModule,
        errorHandlerModule
    )
}