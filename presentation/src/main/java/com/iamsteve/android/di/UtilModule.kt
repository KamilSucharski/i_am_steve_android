package com.iamsteve.android.di

import android.content.Context.MODE_PRIVATE
import com.iamsteve.android.util.implementation.*
import com.iamsteve.android.util.persistence.Persistence
import com.iamsteve.data.util.abstraction.AssetReader
import com.iamsteve.data.util.abstraction.LocalStorage
import com.iamsteve.data.util.abstraction.Serializer
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.abstraction.Logger
import com.iamsteve.domain.util.abstraction.RxSchedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilModule = module {
    single<RxSchedulers> {
        AndroidRxSchedulers(
            observeThread = AndroidSchedulers.mainThread(),
            subscribeThread = Schedulers.io()
        )
    }
    factory<LocalStorage> {
        AndroidLocalStorage(
            sharedPreferences = androidContext().getSharedPreferences(
                Consts.SHARED_PREFERENCES_NAME,
                MODE_PRIVATE
            ),
            filesDirectory = androidContext().filesDir,
            serializer = get()
        )
    }
    factory<Logger> { AndroidLogger() }
    factory<AssetReader> { AndroidAssetReader(assetManager = androidContext().assets) }
    factory<Serializer> { GsonSerializer(get()) }
    factory { Persistence(get()) }
}