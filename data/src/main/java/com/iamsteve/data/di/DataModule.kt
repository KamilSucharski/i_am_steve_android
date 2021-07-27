package com.iamsteve.data.di

object DataModule {

    class Parameters(
        val apiUrl: String,
        val isNetworkLoggingAllowed: Boolean
    )

    fun provide(parameters: Parameters) = listOf(
        repositoryModule,
        apiModule(
            apiUrl = parameters.apiUrl,
            isNetworkLoggingAllowed = parameters.isNetworkLoggingAllowed
        )
    )
}