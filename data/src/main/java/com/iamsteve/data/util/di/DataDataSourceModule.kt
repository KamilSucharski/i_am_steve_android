package com.iamsteve.data.util.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.iamsteve.data.data_source.ApiDataSource
import com.iamsteve.domain.util.abstraction.RxSchedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun dataDataSourceModule(
    apiUrl: String,
    isNetworkLoggingAllowed: Boolean
) = module {

    single<Gson> {
        GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create()
    }

    single<ApiDataSource> {
        val okHttpClient = OkHttpClient
            .Builder()
            .also { builder ->
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
                    .takeIf { isNetworkLoggingAllowed }
                    ?.apply { builder.addInterceptor(this) }
            }
            .build()

        val converterFactory = GsonConverterFactory.create(get())
        val callAdapterFactory = RxJava3CallAdapterFactory
            .createWithScheduler(get<RxSchedulers>().subscribeThread)

        Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl(apiUrl)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build()
            .create(ApiDataSource::class.java)
    }
}