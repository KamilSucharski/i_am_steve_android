package com.iamsteve.android.util.di

import com.iamsteve.android.util.implementation.AndroidLogger
import com.iamsteve.android.util.implementation.GsonSerializer
import com.iamsteve.android.util.persistence.Persistence
import com.iamsteve.data.util.abstraction.Serializer
import com.iamsteve.domain.util.abstraction.Logger
import com.iamsteve.domain.util.abstraction.ReactiveSchedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.dsl.module

val utilModule = module {
    single {
        ReactiveSchedulers(
            observeThread = AndroidSchedulers.mainThread(),
            subscribeThread = Schedulers.io()
        )
    }
    factory<Logger> { AndroidLogger() }
    factory<Serializer> { GsonSerializer(get()) }
    factory { Persistence(get()) }
}