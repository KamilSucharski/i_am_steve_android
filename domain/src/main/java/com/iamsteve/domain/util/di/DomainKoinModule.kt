package com.iamsteve.domain.util.di

object DomainKoinModule : KoinModule<DomainKoinModule.Parameters> {

    class Parameters

    override fun provide(parameters: Parameters) = listOf(
        presenterModule
    )
}
