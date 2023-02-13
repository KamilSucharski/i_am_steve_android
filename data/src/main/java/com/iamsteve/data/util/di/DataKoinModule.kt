package com.iamsteve.data.util.di

import com.iamsteve.domain.util.di.KoinModule

object DataKoinModule : KoinModule<DataKoinModule.Parameters> {

    class Parameters(
        val apiUrl: String,
        val isNetworkLoggingAllowed: Boolean
    )

    override fun provide(parameters: Parameters) = listOf(
        repositoryModule,
        dataDataSourceModule(
            apiUrl = parameters.apiUrl,
            isNetworkLoggingAllowed = parameters.isNetworkLoggingAllowed
        )
    )
}