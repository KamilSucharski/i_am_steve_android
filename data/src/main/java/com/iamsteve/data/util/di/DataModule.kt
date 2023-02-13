package com.iamsteve.data.util.di

import com.iamsteve.domain.util.di.KoinModule

object DataModule : KoinModule<DataModule.Parameters> {

    class Parameters(
        val apiUrl: String,
        val isNetworkLoggingAllowed: Boolean
    )

    override fun provide(parameters: Parameters) = listOf(
        repositoryModule,
        apiModule(
            apiUrl = parameters.apiUrl,
            isNetworkLoggingAllowed = parameters.isNetworkLoggingAllowed
        )
    )
}