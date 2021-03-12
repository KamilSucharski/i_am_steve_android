package com.iamsteve.android.di

import com.iamsteve.domain.util.di.Module

object PresentationModule : Module<PresentationModule.Parameters> {

    class Parameters

    override fun provide(parameters: Parameters) = listOf(
        utilModule,
        presenterModule,
        errorHandlerModule
    )
}