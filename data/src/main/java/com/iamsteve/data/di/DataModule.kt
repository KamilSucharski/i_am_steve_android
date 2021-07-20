package com.iamsteve.data.di

import com.iamsteve.domain.util.di.Module

object DataModule : Module<DataModule.Parameters> {

    class Parameters(
        val apiUrl: String,
        val isNetworkLoggingAllowed: Boolean
    )

    override fun provide(parameters: Parameters) = listOf(
        repositoryModule,
        mapperModule,
        apiModule(
            apiUrl = parameters.apiUrl,
            isNetworkLoggingAllowed = parameters.isNetworkLoggingAllowed
        )
    )
}