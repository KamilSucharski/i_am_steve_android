package com.iamsteve.data.di

import com.google.gson.GsonBuilder
import com.iamsteve.data.api.ComicAPI
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun apiModule(
    apiUrl: String,
    isNetworkLoggingAllowed: Boolean
) = module {

    single<ComicAPI> {
        OkHttpClient.Builder()
            .setTimeouts()
            .addLoggingInterceptor(isNetworkLoggingAllowed)
            .build()
            .let { createWebService(it, apiUrl) }
            .create(ComicAPI::class.java)
    }
}

fun createWebService(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().setLenient().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()
}

fun OkHttpClient.Builder.addLoggingInterceptor(isNetworkLoggingAllowed: Boolean): OkHttpClient.Builder {
    return also { builder ->
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            .takeIf { isNetworkLoggingAllowed }
            ?.apply { builder.addInterceptor(this) }
    }
}

fun OkHttpClient.Builder.setTimeouts(): OkHttpClient.Builder {
    return connectTimeout(2, TimeUnit.MINUTES)
        .writeTimeout(2, TimeUnit.MINUTES)
        .readTimeout(2, TimeUnit.MINUTES)
}