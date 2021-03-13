package com.iamsteve.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.iamsteve.data.api.ComicAPI
import com.iamsteve.data.interceptor.ErrorInterceptor
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

    single<Gson> { GsonBuilder().serializeNulls().setLenient().create() }

    single<ComicAPI> {
        val okHttpClient = OkHttpClient
            .Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(ErrorInterceptor())
            .also { builder ->
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    .takeIf { isNetworkLoggingAllowed }
                    ?.apply { builder.addInterceptor(this) }
            }
            .build()

        Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(ComicAPI::class.java)
    }
}